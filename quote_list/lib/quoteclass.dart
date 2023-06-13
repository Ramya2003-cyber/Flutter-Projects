import 'package:flutter/material.dart';
import 'quote.dart';
class QuoteCard extends StatelessWidget {
  final Quote quote;
  final VoidCallback delete;

  QuoteCard({required this.quote,required this.delete});
  @override
  Widget build(BuildContext context) {
    return Card(
      margin: EdgeInsets.fromLTRB(16.0, 16.0, 16.0, 0),
      color:Colors.red,
      child: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: <Widget>[
            Text(
              quote.text,
              style: TextStyle(
                fontSize: 10.0,
                color: Colors.white,
              ),

            ),
            SizedBox(height: 6.0,),
            Text(
                quote.author,
                style:TextStyle(
                  fontSize: 14.0,
                  color: Colors.grey,
                )
            ),
            SizedBox(height: 8.0,),
            TextButton.icon(
              onPressed: delete,
              label:  Text('Delete Quote',
                  style:TextStyle(
                    color: Colors.white,
                  )
              ),
              icon: Icon(Icons.delete,
                color: Colors.white,
              ),
            )

          ],
        ),
      ),
    );
  }
}
