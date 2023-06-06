import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(
    home:IdCard(),
  ));
}
class IdCard extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return  Scaffold(
      backgroundColor: Colors.grey[900],
      appBar: AppBar(
        title: Text('ID Card'),
        centerTitle: true,
        backgroundColor: Colors.grey[850],
        elevation: 0,
      ),
      body:Padding(
        padding: EdgeInsets.fromLTRB(30.0, 40.0, 30.0, 0.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children:<Widget> [
            Center(
              child: CircleAvatar(
                backgroundImage: AssetImage('images/ramya.jpg'),
                radius: 70.0,

              ),
            ),
            Divider(
              height: 40.0,
              color: Colors.grey,
            ),
            Text(
              'NAME',
              style: TextStyle(
                color: Colors.grey,
                letterSpacing: 2,

              ),
            ),
            SizedBox(height: 10.0,),
            Text(
              'RAMYA SRI',
              style: TextStyle(
                color: Colors.amber[200],
                letterSpacing: 2,
                fontSize: 25,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(height: 30.0,),
            Text(
              'COLLEGE',
              style: TextStyle(
                color: Colors.grey,
                letterSpacing: 2,

              ),
            ),
            SizedBox(height: 10.0,),
            Text(
              'ANDHRA UNIVERSITY',
              style: TextStyle(
                color: Colors.amber[200],
                letterSpacing: 2,
                fontSize: 25,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(height: 30.0,),
            Text(
              'COURSE',
              style: TextStyle(
                color: Colors.grey,
                letterSpacing: 2,

              ),
            ),
            SizedBox(height: 10.0,),
            Text(
              'B.TECH (CSE)',
              style: TextStyle(
                color: Colors.amber[200],
                letterSpacing: 2,
                fontSize: 25,
                fontWeight: FontWeight.bold,
              ),
            ),SizedBox(height: 30.0,),
            Text(
              'CURRENT YEAR',
              style: TextStyle(
                color: Colors.grey,
                letterSpacing: 2,

              ),
            ),
            SizedBox(height: 10.0,),
            Text(
              '4',
              style: TextStyle(
                color: Colors.amber[200],
                letterSpacing: 2,
                fontSize: 25,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(height: 30.0,),
            Row(
              children: [
                Icon(
                  Icons.email,
                  color: Colors.grey[400],
                ),
                SizedBox(width: 10.0,),
                Text(
                  'ramya@gmail.com',
                   style: TextStyle(
                       color:Colors.grey,
                       fontSize: 18.0,
                       letterSpacing: 1,

                   ),

                ),
              ],
            )
          ],
        ),
      )
    );
  }
}


