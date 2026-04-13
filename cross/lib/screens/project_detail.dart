import 'package:flutter/material.dart';
import '../widgets/expense_item.dart';
import 'add_expense.dart';

class ProjectDetailScreen extends StatelessWidget {
  const ProjectDetailScreen({super.key});

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
                      "Project Detail",
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
              child: Column(
                children: [
                  _buildProjectInfoCard(),
                  const SizedBox(height: 12),
                  _buildExpensesCard(context),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildProjectInfoCard() {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _buildInfoRow("Project Name:", "Mobile App Development"),
            const Divider(height: 24),
            _buildInfoRow("Project Code:", "Code: A00001"),
            const Divider(height: 24),
            _buildInfoRow("Owner:", "Nguyen Sy Huong"),
            const Divider(height: 24),
            Row(
              children: [
                _buildVerticalInfo("Start Date", "01/01/2026"),
                _buildVerticalInfo("End Date", "31/12/2026"),
              ],
            ),
            const Divider(height: 24),
            Row(
              children: [
                _buildVerticalInfo("Status", "Active", color: const Color(0xFF1976D2)),
                _buildVerticalInfo("Budget", "\$5,000.00", color: const Color(0xFF2E7D32)),
              ],
            ),
            const Divider(height: 24),
            _buildSectionHeader("Description"),
            const Text("Building a cross-platform app using Flutter.", style: TextStyle(fontSize: 16)),
            const Divider(height: 24),
            _buildSectionHeader("Special Requirements"),
            const Text("None", style: TextStyle(fontSize: 16)),
            const Divider(height: 24),
            _buildSectionHeader("Department Information"),
            const Text("IT Department", style: TextStyle(fontSize: 16)),

          ],
        ),
      ),
    );
  }


  Widget _buildExpensesCard(BuildContext context) {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(5),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Padding(
                  padding: EdgeInsets.all(10),
                  child: Text(
                    "Expenses",
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                  ),
                ),
                ElevatedButton(
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => const AddExpenseScreen(),
                      ),
                    );
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: const Color(0xFF1976D2),
                    foregroundColor: Colors.white,
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                  ),
                  child: const Text("Add Expense"),
                ),
              ],
            ),
          ),
          Container(
            height: 250,
            padding: const EdgeInsets.all(8),
            child: ListView(
              children: const [
                ExpenseItem(
                  id: "EX001",
                  date: "2026-04-14",
                  claimant: "Pham Minh Tuan",
                  type: "Travel",
                  amount: "\$150.00",
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Row(
      children: [
        Text(label, style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 17)),
        const SizedBox(width: 8),
        Expanded(child: Text(value, style: const TextStyle(fontSize: 17))),
      ],
    );
  }

  Widget _buildVerticalInfo(String label, String value, {Color color = Colors.black}) {
    return Expanded(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(label, style: const TextStyle(color: Colors.grey, fontWeight: FontWeight.bold, fontSize: 14)),
          Text(value, style: TextStyle(fontSize: 16, fontWeight: color != Colors.black ? FontWeight.bold : FontWeight.normal, color: color)),
        ],
      ),
    );
  }

  Widget _buildSectionHeader(String title) {
    return Text(title, style: const TextStyle(color: Colors.grey, fontWeight: FontWeight.bold, fontSize: 14));
  }
}