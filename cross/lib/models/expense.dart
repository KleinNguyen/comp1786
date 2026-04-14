class Expense {
  final String id;
  String? projectId;
  String expenseCode;
  String date;
  double amount;
  String currency;
  String type;
  String paymentMethod;
  String claimant;
  String paymentStatus;
  String description;
  String location;

  Expense({
    required this.id,
    this.projectId,
    required this.expenseCode,
    required this.date,
    required this.amount,
    required this.currency,
    required this.type,
    required this.paymentMethod,
    required this.claimant,
    required this.paymentStatus,
    required this.description,
    required this.location,
  });

  factory Expense.fromMap(String id, Map<String, dynamic> map) {
    return Expense(
      id: id,
      projectId: map['projectId']?.toString(),
      expenseCode: map['expenseCode'] ?? '',
      date: map['date'] ?? '',
      amount: (map['amount'] ?? 0.0).toDouble(),
      currency: map['currency'] ?? '',
      type: map['type'] ?? '',
      paymentMethod: map['paymentMethod'] ?? '',
      claimant: map['claimant'] ?? '',
      paymentStatus: map['paymentStatus'] ?? '',
      description: map['description'] ?? '',
      location: map['location'] ?? '',
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'projectId': projectId,
      'expenseCode': expenseCode,
      'date': date,
      'amount': amount,
      'currency': currency,
      'type': type,
      'paymentMethod': paymentMethod,
      'claimant': claimant,
      'paymentStatus': paymentStatus,
      'description': description,
      'location': location,
    };
  }
}