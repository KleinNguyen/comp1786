import 'expense.dart';

class Project {
  final int id;
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
  List<Expense> expenses;

  Project({
    required this.id,
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
    this.expenses = const [],
  });

  factory Project.fromMap(dynamic id, Map<dynamic, dynamic> map) {
    var expenseData = map['expenses'];
    List<Expense> list = [];

    if (expenseData != null) {
      if (expenseData is Map) {
        expenseData.forEach((key, value) {
          list.add(Expense.fromMap(key, Map<String, dynamic>.from(value)));
        });
      } else if (expenseData is List) {
        for (var i = 0; i < expenseData.length; i++) {
          if (expenseData[i] != null) {
            list.add(Expense.fromMap(i, Map<String, dynamic>.from(expenseData[i])));
          }
        }
      }
    }

    return Project(
      id: int.tryParse(id.toString()) ?? 0,
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
      expenses: list,
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
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
      'expenses': expenses.map((e) => e.toMap()).toList(),
    };
  }
}