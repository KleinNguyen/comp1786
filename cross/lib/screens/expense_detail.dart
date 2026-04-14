import 'package:flutter/material.dart';
import 'edit_expense.dart';

class ExpenseDetailScreen extends StatelessWidget {
  final Map<String, dynamic> expenseData;

  const ExpenseDetailScreen({super.key, required this.expenseData});

  Color _getStatusColor(String status) {
    switch (status.toLowerCase()) {
      case "paid":
        return const Color(0xFF2E7D32);
      case "pending":
        return const Color(0xFFFBC02D);
      case "reimbursed":
        return Colors.red;
      default:
        return const Color(0xFF1976D2);
    }
  }

  @override
  Widget build(BuildContext context) {
    final String location = expenseData['location']?.toString() ?? "";
    final String description = expenseData['description']?.toString() ?? "";
    final String currentStatus = expenseData['paymentStatus'] ?? "Pending";

    return Scaffold(
      backgroundColor: const Color(0xFFF5F5F5),
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
                      "Expense Detail",
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
              padding: const EdgeInsets.all(12),
              child: Card(
                elevation: 4,
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      // ĐÃ XÓA DÒNG EXPENSE ID TẠI ĐÂY
                      _buildInfoRow("Expense Code:", expenseData['expenseCode']?.toString() ?? "N/A"),
                      const Divider(height: 24),
                      _buildInfoRow("Claimant:", expenseData['claimant'] ?? "N/A"),
                      const Divider(height: 24),
                      _buildInfoRow("Location:", location.isEmpty ? "None" : location),
                      const Divider(height: 24),
                      _buildInfoRow("Date:", expenseData['date'] ?? "N/A"),
                      const Divider(height: 24),
                      _buildInfoRow("Type:", expenseData['type'] ?? "N/A"),
                      const Divider(height: 24),
                      _buildInfoRow("Payment Method:", expenseData['paymentMethod'] ?? "N/A"),
                      const Divider(height: 24),
                      Row(
                        children: [
                          _buildVerticalInfo(
                              "Status",
                              currentStatus,
                              color: _getStatusColor(currentStatus)
                          ),
                          _buildVerticalInfo(
                              "Amount",
                              "\$${expenseData['amount']?.toString() ?? "0.00"}",
                              color: const Color(0xFF2E7D32)
                          ),
                        ],
                      ),
                      const Divider(height: 24),
                      _buildSectionHeader("Description"),
                      const SizedBox(height: 4),
                      Text(
                        description.isEmpty ? "None" : description,
                        style: const TextStyle(fontSize: 16),
                      ),
                      const Divider(height: 24),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  // ... (Các hàm _buildInfoRow, _buildVerticalInfo, _buildSectionHeader giữ nguyên)
  Widget _buildInfoRow(String label, String value) {
    return Row(
      children: [
        Text(
          label,
          style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 17, color: Colors.black),
        ),
        const SizedBox(width: 8),
        Expanded(
          child: Text(
            value,
            style: const TextStyle(fontSize: 17, color: Colors.black87),
            overflow: TextOverflow.ellipsis,
          ),
        ),
      ],
    );
  }

  Widget _buildVerticalInfo(String label, String value, {Color color = Colors.black}) {
    return Expanded(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            label,
            style: const TextStyle(color: Colors.grey, fontWeight: FontWeight.bold, fontSize: 14),
          ),
          const SizedBox(height: 2),
          Text(
            value,
            style: TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
              color: color,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildSectionHeader(String title) {
    return Text(
      title,
      style: const TextStyle(color: Colors.grey, fontWeight: FontWeight.bold, fontSize: 14),
    );
  }
}