import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';
import '../widgets/expense_item.dart';
import 'add_expense.dart';
import 'expense_detail.dart';
import '../models/project.dart';

class ProjectDetailScreen extends StatelessWidget {
  final Project project;

  const ProjectDetailScreen({super.key, required this.project});

  Color _getStatusColor(String status) {
    switch (status.toLowerCase().trim()) {
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
    final projectRef = FirebaseDatabase.instance.ref().child('projects/${project.id}');

    return StreamBuilder(
      stream: projectRef.onValue,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Scaffold(body: Center(child: CircularProgressIndicator()));
        }

        Project currentProject = project;
        if (snapshot.hasData && snapshot.data!.snapshot.value != null) {
          final data = Map<dynamic, dynamic>.from(snapshot.data!.snapshot.value as Map);
          currentProject = Project.fromMap(project.id, data);
        }

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
                      _buildProjectInfoCard(currentProject),
                      const SizedBox(height: 12),
                      _buildExpensesCard(context, currentProject),
                    ],
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildProjectInfoCard(Project p) {
    return Card(
      elevation: 4,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _buildInfoRow("Project Name:", p.projectName),
            const Divider(height: 24),
            _buildInfoRow("Project Code:", p.projectCode),
            const Divider(height: 24),
            _buildInfoRow("Owner:", p.projectOwner),
            const Divider(height: 24),
            Row(
              children: [
                _buildVerticalInfo("Start Date", p.startDate),
                _buildVerticalInfo("End Date", p.endDate),
              ],
            ),
            const Divider(height: 24),
            Row(
              children: [
                _buildVerticalInfo(
                    "Status",
                    p.projectStatus,
                    color: _getStatusColor(p.projectStatus)
                ),
                _buildVerticalInfo(
                    "Budget",
                    "\$${p.projectBudget.toStringAsFixed(2)}",
                    color: const Color(0xFF2E7D32)
                ),
              ],
            ),
            const Divider(height: 24),
            _buildSectionHeader("Description"),
            Text(p.projectDescription, style: const TextStyle(fontSize: 16)),
            const Divider(height: 24),
            _buildSectionHeader("Special Requirements"),
            Text(p.specialRequirement.isEmpty ? "None" : p.specialRequirement, style: const TextStyle(fontSize: 16)),
            const Divider(height: 24),
            _buildSectionHeader("Department Information"),
            Text(p.departmentInformation.isEmpty ? "None" : p.departmentInformation, style: const TextStyle(fontSize: 16)),
          ],
        ),
      ),
    );
  }

  Widget _buildExpensesCard(BuildContext context, Project p) {
    final databaseRef = FirebaseDatabase.instance.ref().child('projects/${p.id}/expenses');

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
                        builder: (context) => AddExpenseScreen(projectId: p.id),
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
            height: 350,
            padding: const EdgeInsets.all(8),
            child: StreamBuilder(
              stream: databaseRef.onValue,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const Center(child: CircularProgressIndicator());
                }

                if (snapshot.hasData && snapshot.data!.snapshot.value != null) {
                  final dynamic data = snapshot.data!.snapshot.value;
                  List<Map<String, dynamic>> expenseList = [];

                  if (data is Map) {
                    data.forEach((key, value) {
                      expenseList.add(Map<String, dynamic>.from(value as Map));
                    });
                  } else if (data is List) {
                    expenseList = data
                        .where((e) => e != null)
                        .map((e) => Map<String, dynamic>.from(e as Map))
                        .toList();
                  }

                  return ListView.builder(
                    padding: EdgeInsets.zero,
                    itemCount: expenseList.length,
                    itemBuilder: (context, index) {
                      final item = expenseList[index];
                      return InkWell(
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => ExpenseDetailScreen(expenseData: item),
                            ),
                          );
                        },
                        child: ExpenseItem(
                          expenseCode: item['expenseCode']?.toString() ?? "N/A",
                          date: item['date'] ?? "",
                          claimant: item['claimant'] ?? "",
                          type: item['type'] ?? "",
                          amount: "\$${item['amount']?.toString() ?? "0"}",
                        ),
                      );
                    },
                  );
                }

                return const Center(child: Text("No expenses recorded"));
              },
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
          Text(value, style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: color)),
        ],
      ),
    );
  }

  Widget _buildSectionHeader(String title) {
    return Text(title, style: const TextStyle(color: Colors.grey, fontWeight: FontWeight.bold, fontSize: 14));
  }
}