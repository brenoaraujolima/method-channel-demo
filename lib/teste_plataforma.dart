
import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:permission_handler/permission_handler.dart';

class TestePlataforma extends StatefulWidget {
  @override
  TestePlataformaState createState() {
    return new TestePlataformaState();
  }
}

class TestePlataformaState extends State<TestePlataforma> {


  static const platformMethodChannel = const MethodChannel('com.demo/metodochannel');
  String mensagemNativa = '';
  @override
  Widget build(BuildContext context) {
    return new Container(
        color: Colors.deepPurple,
        child: new Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              new Padding(
                padding: const EdgeInsets.only(left: 18.0, top:200.0),
                child: new Text(
                  'Clique no botão',
                  style: new TextStyle(
                      color:Colors.white,
                      fontWeight: FontWeight.w500,
                      fontSize: 23.0),
                ),
              ),
              new Padding(
                padding: const EdgeInsets.only(left: 8.0, right: 8.0, top: 50.0),
                child: new FlatButton(
                  child: new Text('Criar Estudante',
                    style: new TextStyle(
                        color: Colors.black,
                        fontSize: 18.0),
                  ),
                  color: Colors.white,
                  onPressed:() {
                    criarEstudante();
                  },
                ),
              ),
              new Padding(
                padding: const EdgeInsets.only(left: 8.0, right: 8.0, top: 25.0),
                child: new FlatButton(
                  child: new Text('Descolorir Foto',
                    style: new TextStyle(
                        color: Colors.black,
                        fontSize: 18.0),
                  ),
                  color: Colors.white,
                  onPressed:() {
                    print("chamando meu metodo");
                    descolorirFoto();
                    // requestCameraPermission();
                  },
                ),
              ),
              new Padding(
                  padding: const EdgeInsets.only(left:8.0, right:8.0, top: 102.0),
                  child: new Text(
                    mensagemNativa,
                    style: new TextStyle(
                        color: Colors.white,
                        fontWeight: FontWeight.w500,
                        fontSize: 23.0),
                  )
              )
            ]
        )
    );
  }

  Future<Null> criarEstudante() async {
    String _msg;
    try {
      final String result =
      await platformMethodChannel.invokeMethod(
          "criarEstudante",
          {
            "nome": "Breno Araujo",
            "matricula": 123
          }
      );
      _msg = result;
      // print(result);
    } on PlatformException catch(exp) {
      _msg = "Deu muito errado óh ! ${exp.message}";
    }
    setState(() {
      mensagemNativa = _msg;
    });
  }

  Future<Null> descolorirFoto() async {
    String _msg;
    Uint8List bytes;
    var status = await Permission.storage.status;
    if (status.isGranted) {
      File a = File('/storage/emulated/0/Download/go.png');
      bytes = a.readAsBytesSync();
    }
    else if (status.isDenied) {
      if (await Permission.storage.request().isGranted) {
        print('permissao fornecida');
      }
    }

    try {
      final String result = await platformMethodChannel.invokeMethod(
          "descolorirFoto",
          bytes
      );
      final encodedStr = result;
      Uint8List bytesImagem = base64.decode(encodedStr);
      File file = File('/storage/emulated/0/Download/novoArquivo.jpg');
      await file.writeAsBytes(bytesImagem);
    } on PlatformException catch(exp) {
      _msg = "Deu muito errado óh ! ${exp.message}";
    }
    setState(() {});
  }
}
