import 'package:flutter/material.dart';
import '../widgets/project_card.dart';
import '../services/firebase_service.dart';
import '../models/project.dart';
import 'project_detail.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with SingleTickerProviderStateMixin {
  late TabController _tabController;
  final FirebaseService _service = FirebaseService();

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 4, vsync: this);

    _tabController.addListener(() {
      setState(() {});
    });
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Column(
        children: [
          Container(
            padding: const EdgeInsets.only(top: 40, bottom: 10, left: 16, right: 16),
            color: const Color(0xFF1976D2),
            child: const Center(
              child: Text(
                "Project Tracker",
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(12.0),
            child: TextField(
              decoration: InputDecoration(
                hintText: "Search here...",
                prefixIcon: const Icon(Icons.search),
                filled: true,
                fillColor: Colors.grey[200],
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                  borderSide: BorderSide.none,
                ),
                contentPadding: const EdgeInsets.symmetric(vertical: 0),
              ),
            ),
          ),
          TabBar(
            controller: _tabController,
            labelColor: const Color(0xFF1976D2),
            unselectedLabelColor: Colors.grey,
            indicatorColor: const Color(0xFF1976D2),
            indicatorWeight: 4,
            tabs: const [
              Tab(text: "All Project"),
              Tab(text: "Active"),
              Tab(text: "On Hold"),
              Tab(text: "Completed"),
            ],
          ),
          Expanded(
            child: StreamBuilder<List<Project>>(
              stream: _service.getProjects(),
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const Center(child: CircularProgressIndicator());
                }
                if (snapshot.hasError) {
                  return Center(child: Text("Error: ${snapshot.error}"));
                }
                if (!snapshot.hasData || snapshot.data!.isEmpty) {
                  return const Center(child: Text("No projects found."));
                }

                List<Project> filteredProjects = snapshot.data!;

                if (_tabController.index == 1) {
                  filteredProjects = filteredProjects.where((p) => p.projectStatus.toLowerCase() == "active").toList();
                } else if (_tabController.index == 2) {
                  filteredProjects = filteredProjects.where((p) => p.projectStatus.toLowerCase() == "on hold").toList();
                } else if (_tabController.index == 3) {
                  filteredProjects = filteredProjects.where((p) => p.projectStatus.toLowerCase() == "completed").toList();
                }

                return ListView.builder(
                  padding: const EdgeInsets.all(8),
                  itemCount: filteredProjects.length,
                  itemBuilder: (context, index) {
                    final project = filteredProjects[index];
                    return InkWell(
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => ProjectDetailScreen(project: project),
                          ),
                        );
                      },
                      child: ProjectCard(
                        name: project.projectName,
                        code: "Code: ${project.projectCode}",
                        owner: "By ${project.projectOwner}",
                        status: project.projectStatus,
                        budget: project.projectBudget,
                      ),
                    );
                  },
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}