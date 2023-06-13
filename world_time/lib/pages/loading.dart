import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart';
import 'package:world_time/services/world_time.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
class Loading extends StatefulWidget {


  @override
  State<Loading> createState() => _LoadingState();
}




class _LoadingState extends State<Loading> {

  void setupWorldTime() async
  {
    WorldTime instance=WorldTime(location: 'India', url: 'Asia/Kolkata');
    await instance.getTime();
    Navigator.pushReplacementNamed(context, '/home',arguments: {
      'location':instance.location,
       'time':instance.time1,
      'isDayTime':instance.isDayTime,
    });



  }
  @override
  void initState(){
    super.initState();
    setupWorldTime();
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.blue[900],
     body: Center(
         child:SpinKitFadingCircle(
           color: Colors.white,
           size: 80.0,
         ),
     ),
    );
  }
}
