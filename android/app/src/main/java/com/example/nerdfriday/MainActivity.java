package com.example.nerdfriday;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.view.FlutterView;

import android.content.Context;
import androidx.annotation.NonNull;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.graphics.*;

import java.lang.Object.*;

import java.util.Base64;
import java.io.*;


import com.example.nerdfriday.Estudante;

public class MainActivity extends FlutterActivity {
	private static final String CHANNEL = "com.demo/metodochannel";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
		        GeneratedPluginRegistrant.registerWith(flutterEngine);
		        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
					.setMethodCallHandler(
						(call, result) -> {
							switch(call.method) {
								case "criarEstudante":
									String nome = call.argument("nome");
									int matricula = call.argument("matricula");
									Estudante estudante = new Estudante(nome, matricula);
									result.success(estudante.getSaudacao());
									break;
								case "descolorirFoto":
									byte[] bytesImagem = call.arguments();

									BitmapFactory.Options options = new BitmapFactory.Options();
									options.inMutable = true;
									Bitmap bmpOriginal = BitmapFactory.decodeByteArray(bytesImagem, 0, bytesImagem.length, options);
									Bitmap novoBmp = descolorirBmp(bmpOriginal);

									ByteArrayOutputStream stream = new ByteArrayOutputStream();
									novoBmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
									byte[] byteArray = stream.toByteArray();
									novoBmp.recycle();

									String base64encoded = Base64.getEncoder().encodeToString(byteArray);

									result.success(base64encoded);
									break;
								default:
									result.notImplemented();
							}
						}
					);
	}

	private Bitmap descolorirBmp(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}
}

