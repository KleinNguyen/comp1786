import 'package:flutter/material.dart';
import '../widgets/expense_item.dart';
import 'add_expense.dart';
import '../models/project.dart';

class ProjectDetailScreen extends StatelessWidget {
  final Project project;

  const ProjectDetailScreen({super.key, required this.project});

  Color _getStatusColor(String status) {
    switch (status.toLowerCase()) {
      case "active":
        return const Color(0xFF1976D2);
      case "on hold":
        return const Color(0xFFFBC02D);
      case "completed":
        return const Color(0xFF2E7D32);
      default:
        return Colors.black;
    }
  }

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
            _buildInfoRow("Project Name:", project.projectName),
            const Divider(height: 24),
            _buildInfoRow("Project Code:", "Code: ${project.projectCode}"),
            const Divider(height: 24),
            _buildInfoRow("Owner:", project.projectOwner),
            const Divider(height: 24),
            Row(
              children: [
                _buildVerticalInfo("Start Date", project.startDate),
                _buildVerticalInfo("End Date", project.endDate),
              ],
            ),
            const Divider(height: 24),
            Row(
              children: [
                _buildVerticalInfo(
                    "Status",
                    project.projectStatus,
                    color: _getStatusColor(project.projectStatus)
                ),
                _buildVerticalInfo(
                    "Budget",
                    "\$${project.projectBudget.toStringAsFixed(2)}",
                    color: const Color(0xFF2E7D32)
                ),
              ],
            ),
            const Divider(height: 24),
            _buildSectionHeader("Description"),
            Text(project.projectDescription, style: const TextStyle(fontSize: 16)),
            const Divider(height: 24),
            _buildSectionHeader("Special Requirements"),
            Text(project.specialRequirement.isEmpty ? "None" : project.specialRequirement, style: const TextStyle(fontSize: 16)),
            const Divider(height: 24),
            _buildSectionHeader("Department Information"),
            Text(project.departmentInformation.isEmpty ? "N/A" : project.departmentInformation, style: const TextStyle(fontSize: 16)),
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
            child: const Center(child: Text("No expenses recorded")),
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
          Text(value, style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: color)),
        ],
      ),
    );
  }

  Widget _buildSectionHeader(String title) {
    return Text(title, style: const TextStyle(color: Colors.grey, fontWeight: FontWeight.bold, fontSize: 14));
  }
}