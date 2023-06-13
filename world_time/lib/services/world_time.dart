import 'dart:convert';
import 'package:http/http.dart';
import 'package:intl/intl.dart';

class WorldTime{
  late String location;
  late String time1;
  late String url;
  bool isDayTime=false;
  WorldTime({required this.location,required this.url});


  Future<void> getTime() async {
    try {
      Response respose = await get(
          Uri.parse('http://worldtimeapi.org/api/timezone/$url'));
      Map time = jsonDecode(respose.body);
      String datetime = time['datetime'];
      String a = time['utc_offset'].substring(0, 1);
      String offset = time['utc_offset'].substring(1, 3);
      String min = time['utc_offset'].substring(4, 6);
      //create a DateTime Object
      DateTime now = DateTime.parse(datetime);
      //add offset to time
      if (a == "+") {
        now = now.add(Duration(hours: int.parse(offset)));
        now = now.add(Duration(minutes: int.parse(min)));
      }
      else {
        now = now.subtract(Duration(hours: int.parse(offset)));
        now = now.subtract(Duration(minutes: int.parse(min)));
      }
      time1 = DateFormat.jm().format(now);
      if(now.hour>=6 && now.hour <=18)
        {
          isDayTime=true;
        }
    }
    catch(e){
      print("Caught error:$e");
      time1='could not get time data';
    }


  }
}


