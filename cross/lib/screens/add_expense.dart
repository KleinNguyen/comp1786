import 'package:flutter/material.dart';
import '../services/firebase_service.dart';

class AddExpenseScreen extends StatefulWidget {
  final int projectId;

  const AddExpenseScreen({super.key, required this.projectId});

  @override
  State<AddExpenseScreen> createState() => _AddExpenseScreenState();
}

class _AddExpenseScreenState extends State<AddExpenseScreen> {
  final FirebaseService _service = FirebaseService();
  final TextEditingController _codeController = TextEditingController();
  final TextEditingController _dateController = TextEditingController();
  final TextEditingController _amountController = TextEditingController();
  final TextEditingController _currencyController = TextEditingController(text: "USD");
  final TextEditingController _claimantController = TextEditingController();
  final TextEditingController _locationController = TextEditingController();
  final TextEditingController _descriptionController = TextEditingController();

  String? _selectedType;
  String? _selectedPaymentMethod;
  String? _selectedStatus;

  final List<String> _expenseTypes = [
    "Travel",
    "Equipment",
    "Materials",
    "Service",
    "Software/Licenses",
    "Labour costs",
    "Utilities",
    "Miscellaneous"
  ];
  final List<String> _paymentMethods = [
    "Cash",
    "Credit Card",
    "Bank Transfer",
    "Cheque"
  ];
  final List<String> _statusOptions = [
    "Pending",
    "Paid",
    "Reimbursed"
  ];

  Future<void> _handleSave() async {
    final String codeText = _codeController.text.trim();

    if (codeText.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Please enter Expense Code")),
      );
      return;
    }

    if (_dateController.text.trim().isEmpty ||
        _amountController.text.trim().isEmpty ||
        _currencyController.text.trim().isEmpty ||
        _claimantController.text.trim().isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Please fill all required fields")),
      );
      return;
    }

    if (_selectedType == null ||
        _selectedPaymentMethod == null ||
        _selectedStatus == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Please select Type, Payment Method, and Status")),
      );
      return;
    }

    final Map<String, dynamic> expenseData = {
      "expenseCode": codeText, // Lưu là String
      "date": _dateController.text.trim(),
      "amount": double.tryParse(_amountController.text.trim()) ?? 0.0,
      "currency": _currencyController.text.trim(),
      "type": _selectedType,
      "paymentMethod": _selectedPaymentMethod,
      "claimant": _claimantController.text.trim(),
      "paymentStatus": _selectedStatus,
      "location": _locationController.text.trim(),
      "description": _descriptionController.text.trim(),
    };

    try {
      await _service.addExpense(widget.projectId, expenseData);
      if (mounted) {
        Navigator.pop(context);
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("Error: $e")),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Column(
        children: [
          Container(
            height: 100,
            padding: const EdgeInsets.only(top: 40, left: 8, right: 8),
            color: const Color(0xFF1976D2),
            child: Row(
              children: [
                IconButton(
                  icon: const Icon(Icons.arrow_back, color: Colors.white, size: 28),
                  onPressed: () => Navigator.pop(context),
                ),
                const Expanded(
                  child: Center(
                    child: Text(
                      "Add Expense",
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 24,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ),
                const SizedBox(width: 48),
              ],
            ),
          ),
          Expanded(
            child: SingleChildScrollView(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                children: [
                  // Sửa label hiển thị thành Expense Code
                  _buildTextField("Expense Code", _codeController),
                  _buildTextField(
                      "Date of Expense",
                      _dateController,
                      readOnly: true,
                      onTap: () async {
                        DateTime? pickedDate = await showDatePicker(
                          context: context,
                          initialDate: DateTime.now(),
                          firstDate: DateTime(2000),
                          lastDate: DateTime(2100),
                        );
                        if (pickedDate != null) {
                          setState(() {
                            _dateController.text = pickedDate.toString().split(' ')[0];
                          });
                        }
                      }
                  ),
                  Row(
                    children: [
                      Expanded(
                        flex: 2,
                        child: _buildTextField("Amount", _amountController, keyboardType: TextInputType.number),
                      ),
                      const SizedBox(width: 8),
                      Expanded(
                        flex: 1,
                        child: _buildTextField("Currency", _currencyController),
                      ),
                    ],
                  ),
                  _buildDropdown("Type of Expense", _expenseTypes, (val) => setState(() => _selectedType = val)),
                  _buildDropdown("Payment Method", _paymentMethods, (val) => setState(() => _selectedPaymentMethod = val)),
                  _buildTextField("Claimant", _claimantController),
                  _buildDropdown("Payment Status", _statusOptions, (val) => setState(() => _selectedStatus = val)),
                  _buildTextField("Location", _locationController),
                  _buildTextField(
                      "Description",
                      _descriptionController,
                      maxLines: 3,
                      keyboardType: TextInputType.multiline
                  ),
                  const SizedBox(height: 10),
                  SizedBox(
                    width: double.infinity,
                    height: 55,
                    child: ElevatedButton(
                      onPressed: _handleSave,
                      style: ElevatedButton.styleFrom(
                        backgroundColor: const Color(0xFF1976D2),
                        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                      ),
                      child: const Text(
                        "Save Expense",
                        style: TextStyle(color: Colors.white, fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                    ),
                  ),
                  const SizedBox(height: 16),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildTextField(String label, TextEditingController controller,
      {bool readOnly = false, VoidCallback? onTap, TextInputType keyboardType = TextInputType.text, int maxLines = 1}) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12.0),
      child: TextField(
        controller: controller,
        readOnly: readOnly,
        onTap: onTap,
        keyboardType: keyboardType,
        maxLines: maxLines,
        decoration: InputDecoration(
          labelText: label,
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(8)),
          contentPadding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
        ),
      ),
    );
  }

  Widget _buildDropdown(String label, List<String> items, Function(String?) onChanged) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12.0),
      child: DropdownButtonFormField<String>(
        decoration: InputDecoration(
          labelText: label,
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(8)),
          contentPadding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
        ),
        items: items.map((String value) {
          return DropdownMenuItem<String>(
            value: value,
            child: Text(value),
          );
        }).toList(),
        onChanged: onChanged,
      ),
    );
  }
}