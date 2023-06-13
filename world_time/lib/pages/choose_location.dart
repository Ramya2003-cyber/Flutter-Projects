import 'package:flutter/material.dart';
import 'package:http/http.dart';
import 'dart:convert';
import 'package:world_time/services/world_time.dart';

class ChooseLocation extends StatefulWidget {

  @override
  State<ChooseLocation> createState() => _ChooseLocationState();
}


class _ChooseLocationState extends State<ChooseLocation> {
  List<String> timezones=[];
  @override
  void initState() {
    super.initState();
    fetchTimezones();
  }
  Future<void> fetchTimezones() async{
    Response response=await get(Uri.parse("https://worldtimeapi.org/api/timezones"));
    if(response.statusCode==200)
      {
        List<dynamic> data=jsonDecode(response.body);
        setState(() {
          timezones=List<String>.from(data);
        });
      }
  }
  void setupWorldTime(int index) async
  {
    int a = timezones[index].indexOf('/');
    WorldTime instance = WorldTime(
        location: timezones[index].substring(a + 1), url: timezones[index]);
    await instance.getTime();
    Navigator.pop(context,  {
      'location': instance.location,
      'time': instance.time1,
      'isDayTime': instance.isDayTime,
    });
  }
  @override
  Widget build(BuildContext context) {

    return Scaffold(
      backgroundColor: Colors.grey[200],
      appBar: AppBar(
        backgroundColor: Colors.blue[900],
        title: Text('Choose a Location '),
        centerTitle: true,
        elevation: 0,
      ),
      body: SingleChildScrollView(
        physics: ScrollPhysics(),
        child: Column(
          children: [
            ListView.builder(
               shrinkWrap: true,
                physics: ScrollPhysics(),
                itemCount: timezones.length,
                itemBuilder: (context,index){
                  return Card(
                   child: ListTile(
                     onTap: (){
                       setupWorldTime(index);
                     },
                     title: Text(timezones[index]),
                   ),
                  );
                },
            ),
          ],
        ),
      ),


    );
  }
}
