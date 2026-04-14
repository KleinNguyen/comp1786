import 'package:flutter/material.dart';

class ProjectCard extends StatelessWidget {
  final int id;
  final String name;
  final String code;
  final String owner;
  final String status;
  final double budget;

  const ProjectCard({
    super.key,
    required this.id,
    required this.name,
    required this.code,
    required this.owner,
    required this.status,
    required this.budget,
  });

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
    return Card(
      elevation: 2,
      margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 4),
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Expanded(
                  child: Text(
                    name,
                    style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 4),
            Text(code, style: const TextStyle(color: Colors.grey)),
            Text(owner, style: const TextStyle(color: Colors.grey)),
            const SizedBox(height: 8),
            Row(
              children: [
                const Text("Status: "),
                Text(
                  status,
                  style: TextStyle(
                    color: _getStatusColor(status),
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            Row(
              children: [
                const Text("Budget: "),
                Text(
                  budget.toStringAsFixed(2),
                  style: const TextStyle(
                    color: Color(0xFF2E7D32),
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}