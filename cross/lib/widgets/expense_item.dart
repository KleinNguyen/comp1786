import 'package:flutter/material.dart';

class ExpenseItem extends StatelessWidget {
  final String id;
  final String date;
  final String claimant;
  final String type;
  final String amount;

  const ExpenseItem({
    super.key,
    required this.id,
    required this.date,
    required this.claimant,
    required this.type,
    required this.amount,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 2,
      margin: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              id,
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 6),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Expanded(
                  child: Text(
                    date,
                    style: const TextStyle(color: Color(0xFF757575), fontSize: 14),
                  ),
                ),
                Expanded(
                  child: Text(
                    "By: $claimant",
                    textAlign: TextAlign.end,
                    style: const TextStyle(color: Color(0xFF757575), fontSize: 14),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 4),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Expanded(
                  child: Text(
                    type,
                    style: const TextStyle(color: Colors.black, fontSize: 16),
                  ),
                ),
                Text(
                  amount,
                  style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}