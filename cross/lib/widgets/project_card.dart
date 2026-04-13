import 'package:flutter/material.dart';
import '../screens/project_detail.dart';

class ProjectCard extends StatelessWidget {
  final String name;
  final String code;
  final String owner;
  final String status;
  final String budget;

  const ProjectCard({
    super.key,
    required this.name,
    required this.code,
    required this.owner,
    required this.status,
    required this.budget,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 6,
      margin: const EdgeInsets.symmetric(vertical: 8),
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
      child: InkWell(
        borderRadius: BorderRadius.circular(20),
        onTap: () {

          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => const ProjectDetailScreen(),
            ),
          );
        },
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                name,
                style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 8),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Expanded(child: Text(code, style: const TextStyle(color: Colors.grey))),
                  Expanded(child: Text(owner, textAlign: TextAlign.end, style: const TextStyle(color: Colors.grey))),
                ],
              ),
              const SizedBox(height: 8),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    status,
                    style: const TextStyle(fontSize: 17, fontWeight: FontWeight.bold, color: Color(0xFF1976D2)),
                  ),
                  Text(
                    budget,
                    style: const TextStyle(fontSize: 17, fontWeight: FontWeight.bold, color: Color(0xFF2E7D32)),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}