import 'package:flutter/material.dart';
import '../widgets/project_card.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with SingleTickerProviderStateMixin {
  late TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 4, vsync: this);
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
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Spacer(),
                const Text(
                  "Project Tracker",
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const Spacer(),
              ],
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
            child: ListView(
              padding: const EdgeInsets.all(8),
              children: const [
                ProjectCard(
                  name: "Mobile App Development",
                  code: "Code: A00001",
                  owner: "By Nguyen Sy Huong",
                  status: "Active",
                  budget: "\$5000",
                ),
                ProjectCard(
                  name: "Game Metroidvania",
                  code: "Code: G00002",
                  owner: "By Nguyen Sy Huong",
                  status: "Pending",
                  budget: "\$2500",
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}