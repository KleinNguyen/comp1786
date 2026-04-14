import 'package:firebase_database/firebase_database.dart';
import '../models/project.dart';

class FirebaseService {
  final DatabaseReference _dbRef = FirebaseDatabase.instance.ref();

  Stream<List<Project>> getProjects() {
    return _dbRef.child('projects').onValue.map((event) {
      final data = event.snapshot.value;
      List<Project> projects = [];

      if (data != null) {
        if (data is List) {
          for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
              projects.add(Project.fromMap(i, Map<dynamic, dynamic>.from(data[i])));
            }
          }
        } else if (data is Map) {
          data.forEach((key, value) {
            projects.add(Project.fromMap(key, Map<dynamic, dynamic>.from(value)));
          });
        }
      }
      return projects;
    });
  }

  Future<void> addProject(Project project) async {
    await _dbRef.child('projects').child(project.id.toString()).set(project.toMap());
  }

  Future<void> addExpense(int projectId, Map<String, dynamic> expenseData) async {
    final DatabaseReference expenseRef = _dbRef.child("projects/$projectId/expenses");

    try {
      final snapshot = await expenseRef.get();
      int newId = 0;

      if (snapshot.exists) {
        final data = snapshot.value;
        if (data is List) {
          newId = data.length;
        } else if (data is Map) {
          List<int> keys = data.keys
              .map((e) => int.tryParse(e.toString()) ?? -1)
              .where((e) => e != -1)
              .toList();
          if (keys.isNotEmpty) {
            keys.sort();
            newId = keys.last + 1;
          }
        }
      }
      final Map<String, dynamic> finalData = {
        ...expenseData,
        "id": newId,
      };

      await expenseRef.child(newId.toString()).set(finalData);
    } catch (e) {
      rethrow;
    }
  }

  Future<void> syncAllProjects(List<Project> localProjects) async {
    Map<String, dynamic> updates = {};
    for (var project in localProjects) {
      updates['projects/${project.id}'] = project.toMap();
    }
    await _dbRef.update(updates);
  }
}