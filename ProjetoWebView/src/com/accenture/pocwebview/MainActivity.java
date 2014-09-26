package com.accenture.pocwebview;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * @author jonathan.s.santos
 *
 * @version 25/09/2014
 * @project PocWebView
 */
@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

	private WebView webview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		webview = (WebView) findViewById(R.id.webview);
		webview.setWebViewClient(new MyWebViewClient());
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(this, "pocwebview");
		webview.loadUrl("file:///android_asset/simplesHTML.html");
		
	}
	
	//Anota��o que identifica este m�todo como acess�vel por javascript
	@JavascriptInterface
	public void metodoActivity(String dadosFormulario){
		//Apenas para imprimir no console o que recebemos de nosso formul�rio
		Log.i("MainActivity","Dados Recebidos: \n" + dadosFormulario);
		
		try {
			//Convertemos a String que recebemos em JSON
			JSONObject dadosJson = new JSONObject(dadosFormulario);
			//Recuperamos o nome digitado no formul�rio
			String nomeRecebido = dadosJson.getString("param_nome");
			//Recarregamos nossa webview com os dados retornados desta fun��o javascript
			webview.loadUrl("javascript:metodoWebView('" + dadosFormulario + "');");
			//Mostrando um 'alert' com o nome recebido que recuperamos do formul�rio
			Toast.makeText(this, nomeRecebido, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.e("MainActivity", e.getMessage());
		}
	}
	
	/**
	 * @author jonathan.s.santos
	 *
	 * @version 25/09/2014
	 * @project PocWebView
	 */
	private class MyWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return false;
		}
	}
}
