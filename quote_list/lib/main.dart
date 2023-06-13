import 'package:flutter/material.dart';
import 'quote.dart';
import 'quoteclass.dart';
void main(){
  runApp(MaterialApp(
    home: QuoteList(),
  ));
}
class QuoteList extends StatefulWidget {

  @override
  State<QuoteList> createState() => _QuoteListState();
}

class _QuoteListState extends State<QuoteList> {
  List<Quote> quotes=[
    Quote(author: 'Steve Jobs',text:'The only way to do great work is to love what you do'),
    Quote(author: 'Abraham Lincoln',text:'In the end, it\'s not the years in your life that count. It\'s the life in your years'),
    Quote(author: 'Winston Churchill',text:'Success is not final, failure is not fatal: It is the courage to continue that counts'),
   ];

  
  @override
  Widget build(BuildContext context) {
    return  Scaffold(
      backgroundColor: Colors.grey[200],
     appBar: AppBar(
       title: Text('Awesome Quote List'),
       backgroundColor: Colors.redAccent,
       centerTitle: true,
     ),
      body:Column(
        children: quotes.map((quote){
          return QuoteCard(
              quote:quote,
              delete:(){
                setState(() {
                  quotes.remove(quote);
                });
              }
          );

        }).toList(),
            // map iterates through the list of data
      )
    );
  }
}

