import 'expense.dart';

class Project {
  String? id;
  String projectCode;
  String projectName;
  String projectDescription;
  String startDate;
  String endDate;
  String projectOwner;
  String projectStatus;
  double projectBudget;
  String specialRequirement;
  String departmentInformation;
  List<Expense>? expenses;

  Project({
    this.id,
    required this.projectCode,
    required this.projectName,
    required this.projectDescription,
    required this.startDate,
    required this.endDate,
    required this.projectOwner,
    required this.projectStatus,
    required this.projectBudget,
    required this.specialRequirement,
    required this.departmentInformation,
    this.expenses,
  });

  factory Project.fromMap(String id, Map<String, dynamic> map) {
    return Project(
      id: id,
      projectCode: map['projectCode'] ?? '',
      projectName: map['projectName'] ?? '',
      projectDescription: map['projectDescription'] ?? '',
      startDate: map['startDate'] ?? '',
      endDate: map['endDate'] ?? '',
      projectOwner: map['projectOwner'] ?? '',
      projectStatus: map['projectStatus'] ?? '',
      projectBudget: (map['projectBudget'] ?? 0.0).toDouble(),
      specialRequirement: map['specialRequirement'] ?? '',
      departmentInformation: map['departmentInformation'] ?? '',
      expenses: [],
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'projectCode': projectCode,
      'projectName': projectName,
      'projectDescription': projectDescription,
      'startDate': startDate,
      'endDate': endDate,
      'projectOwner': projectOwner,
      'projectStatus': projectStatus,
      'projectBudget': projectBudget,
      'specialRequirement': specialRequirement,
      'departmentInformation': departmentInformation,
    };
  }
}