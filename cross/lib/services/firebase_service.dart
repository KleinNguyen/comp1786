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
              projects.add(Project.fromMap(i.toString(), Map<String, dynamic>.from(data[i])));
            }
          }
        } else if (data is Map) {
          data.forEach((key, value) {
            projects.add(Project.fromMap(key.toString(), Map<String, dynamic>.from(value)));
          });
        }
      }
      return projects;
    });
  }
}