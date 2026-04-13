import 'package:flutter/material.dart';
import 'edit_expense.dart';
class ExpenseDetailScreen extends StatelessWidget {
  const ExpenseDetailScreen({super.key});

  @override
  Widget build(BuildContext context) {
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

          // Main Content
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
                      _buildInfoRow("Expense ID:", "EX001"),
                      const Divider(height: 24),

                      _buildInfoRow("Claimant:", "Pham Minh Tuan"),
                      const Divider(height: 24),

                      _buildInfoRow("Location:", "Hanoi, Vietnam"),
                      const Divider(height: 24),

                      _buildInfoRow("Date:", "2026-04-14"),
                      const Divider(height: 24),

                      _buildInfoRow("Type:", "Travel"),
                      const Divider(height: 24),

                      _buildInfoRow("Payment Method:", "Credit Card"),
                      const Divider(height: 24),

                      Row(
                        children: [
                          _buildVerticalInfo("Status", "Paid", color: const Color(0xFF1976D2)),
                          _buildVerticalInfo("Amount", "\$150.00", color: const Color(0xFF2E7D32)),
                        ],
                      ),
                      const Divider(height: 24),

                      _buildSectionHeader("Description"),
                      const SizedBox(height: 4),
                      const Text(
                        "Taxi fare for meeting with client at the airport.",
                        style: TextStyle(fontSize: 16),
                      ),
                      const Divider(height: 24),

                      Row(
                        children: [
                          Expanded(
                            child: OutlinedButton(
                              onPressed: () {
                              },
                              style: OutlinedButton.styleFrom(
                                foregroundColor: Colors.red,
                                side: const BorderSide(color: Colors.red),
                                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                                padding: const EdgeInsets.symmetric(vertical: 12),
                              ),
                              child: const Text("Delete", style: TextStyle(fontSize: 16)),
                            ),
                          ),
                          const SizedBox(width: 16),
                          Expanded(
                            child: ElevatedButton(
                              onPressed: () {
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => EditExpenseScreen(
                                      expenseData: {
                                        'id': 'EX001',
                                        'date': '2026-04-14',
                                        'amount': 150.00,
                                        'currency': 'USD',
                                        'type': 'Travel',
                                        'paymentMethod': 'Credit Card',
                                        'claimant': 'Pham Minh Tuan',
                                        'status': 'Paid',
                                        'location': 'Hanoi, Vietnam',
                                        'description': 'Taxi fare for meeting with client at the airport.',
                                      },
                                    ),
                                  ),
                                );
                              },
                              style: ElevatedButton.styleFrom(
                                backgroundColor: const Color(0xFF1976D2),
                                foregroundColor: Colors.white,
                                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                                padding: const EdgeInsets.symmetric(vertical: 12),
                              ),
                              child: const Text("Edit Expense", style: TextStyle(fontSize: 16)),
                            ),
                          ),
                        ],
                      ),
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