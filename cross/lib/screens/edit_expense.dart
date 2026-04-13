import 'package:flutter/material.dart';

class EditExpenseScreen extends StatefulWidget {
  final Map<String, dynamic> expenseData;

  const EditExpenseScreen({super.key, required this.expenseData});

  @override
  State<EditExpenseScreen> createState() => _EditExpenseScreenState();
}

class _EditExpenseScreenState extends State<EditExpenseScreen> {
  late TextEditingController _idController;
  late TextEditingController _dateController;
  late TextEditingController _amountController;
  late TextEditingController _currencyController;
  late TextEditingController _claimantController;
  late TextEditingController _locationController;
  late TextEditingController _descriptionController;

  String? _selectedType;
  String? _selectedPaymentMethod;
  String? _selectedStatus;

  final List<String> _expenseTypes = ['Travel', 'Food', 'Equipment', 'Other'];
  final List<String> _paymentMethods = ['Cash', 'Credit Card', 'Bank Transfer'];
  final List<String> _statusOptions = ['Pending', 'Paid', 'Cancelled'];

  @override
  void initState() {
    super.initState();
    _idController = TextEditingController(text: widget.expenseData['id']);
    _dateController = TextEditingController(text: widget.expenseData['date']);
    _amountController = TextEditingController(text: widget.expenseData['amount'].toString());
    _currencyController = TextEditingController(text: widget.expenseData['currency'] ?? "USD");
    _claimantController = TextEditingController(text: widget.expenseData['claimant']);
    _locationController = TextEditingController(text: widget.expenseData['location']);
    _descriptionController = TextEditingController(text: widget.expenseData['description']);

    _selectedType = widget.expenseData['type'];
    _selectedPaymentMethod = widget.expenseData['paymentMethod'];
    _selectedStatus = widget.expenseData['status'];
  }

  @override
  void dispose() {
    _idController.dispose();
    _dateController.dispose();
    _amountController.dispose();
    _currencyController.dispose();
    _claimantController.dispose();
    _locationController.dispose();
    _descriptionController.dispose();
    super.dispose();
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
                      "Edit Expense",
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
                  _buildTextField("Expense ID", _idController),
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

                  _buildDropdown("Type of Expense", _expenseTypes, _selectedType, (val) => setState(() => _selectedType = val)),
                  _buildDropdown("Payment Method", _paymentMethods, _selectedPaymentMethod, (val) => setState(() => _selectedPaymentMethod = val)),

                  _buildTextField("Claimant", _claimantController),

                  _buildDropdown("Payment Status", _statusOptions, _selectedStatus, (val) => setState(() => _selectedStatus = val)),

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
                      onPressed: () {
                      },
                      style: ElevatedButton.styleFrom(
                        backgroundColor: const Color(0xFF1976D2),
                        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                      ),
                      child: const Text(
                        "Update Expense",
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
          filled: readOnly,
          fillColor: readOnly ? Colors.grey[100] : null,
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(8)),
          contentPadding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
        ),
      ),
    );
  }

  Widget _buildDropdown(String label, List<String> items, String? currentValue, Function(String?) onChanged) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 12.0),
      child: DropdownButtonFormField<String>(
        value: currentValue,
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