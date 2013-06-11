package avijit.android.tipcalculatorextreme;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Editable;
import android.text.InputType;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivityActivity extends Activity {

	private Spinner spinnerPercent;
	private Spinner spinnerPerson;
    private EditText txtPrice;
    private TextView txtTotal;
    private TextView txtPercentOuput;
    private TableLayout tableLayoutMain;
    private TextView txtSplittedAmount;
    private Button buttonSpinner;
    private EditText txtPercent;
    
    private SharedPreferences sharedPref;
    private Editor prefEditor;
    private static final String MYPREFS = "myPreference";
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		//addPreferencesFromResource(R.layout.preferencemain);
		
		spinnerPercent=(Spinner)findViewById(R.id.spinnerPercent);
        txtPrice = (EditText)findViewById(R.id.txtPrice);
        txtTotal = (TextView)findViewById(R.id.txtTotal);
        txtPercentOuput = (TextView)findViewById(R.id.txtPercentOuput);
        txtSplittedAmount = (TextView)findViewById(R.id.txtSplittedAmount);
        spinnerPerson=(Spinner)findViewById(R.id.spinnerPerson);
        tableLayoutMain=(TableLayout)findViewById(R.id.TableLayoutMain);
        tableLayoutMain.setBackgroundResource(R.drawable.background);
        buttonSpinner = (Button)findViewById(R.id.btnSpinner);
        txtPercent = (EditText)findViewById(R.id.txtPercent);
        
        //Get fromOrient value from SharedPreference
		//SharedPreferences mySharedPreferences = getSharedPreferences(MyOrientationPref,Activity.MODE_PRIVATE);
		//fromOrientation = mySharedPreferences.getBoolean("fromOrient",false);
        
    	//loadPreferences();
        fillSpinner(spinnerPerson, 1, 30);
        fillSpinner(spinnerPercent, 0, 100);
             
        //Spinner tableLayoutMain
        spinnerPercent.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
            	clickButton(tableLayoutMain);
            	
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        
        //Spinner tableLayoutMain
        spinnerPerson.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
            	clickButton(tableLayoutMain);
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        

        //TextChanged Listner
        //============================================================================================
        txtPercent.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            	//clickButton(tableLayoutMain);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	
            } 

        });
      //============================================================================================
        
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		AlertDialog alertDialog;
		
	    switch (item.getItemId()) {
	    
	        case R.id.menu_close:
	        	System.exit(0);
	        	return true;
	        	
	        case R.id.menu_about:
	        	alertDialog = new AlertDialog.Builder(this).create();
	        	alertDialog.setTitle(getResources().getString(R.string.title_about));
	        	alertDialog.setMessage(getResources().getString(R.string.title_about_msg));
	        	alertDialog.show();
	        	return true;
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		 	//
		 }

	//This is to hide the soft keyboard
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	
	public void clickButton(View v){
		hideSoftKeyboard(this);
		double txtTip=0;
		int intPerson=0;
		DecimalFormat df=new DecimalFormat("#.##");
		
		try
		{

			if(spinnerPercent.isShown()){
				txtTip=Double.parseDouble(spinnerPercent.getSelectedItem().toString()); //Get the value from selected Spinner row
			}
			else if(txtPercent.isShown()){
				txtTip=Double.parseDouble(txtPercent.getText().toString());
			}

			intPerson=(Integer)spinnerPerson.getSelectedItem();


			double tip;
			double price;
			double tipTotal;
			double totalPrice;
			double splittedAmt;

			//For number format
			String strPrice=txtPrice.getText().toString();

			if(strPrice == null){
				return;
			}


			price=Double.parseDouble(txtPrice.getText().toString());
			tip=txtTip;
			tipTotal=(tip*price)/100;
			totalPrice=price + tipTotal;
			splittedAmt=totalPrice/intPerson;


			String answer="";
			answer=df.format(totalPrice);                      
			txtPercentOuput.setText(df.format(tipTotal));
			txtTotal.setText(answer) ;
			txtSplittedAmount.setText(df.format(splittedAmt));
			//savePreferences();
		}
		catch(Exception e)
		{

			Log.e("ERROR:", e.getStackTrace().toString());
		}
		finally
		{
			df=null;
		}
	}
	
	/**
	 * Click on the Custom Button
	 * 
	 * @param v
	 */
	public void onClickBtnSpinner(View v){
		hideSoftKeyboard(this);
		if(buttonSpinner.getText().toString().equalsIgnoreCase("Custom")){
			spinnerPercent.setVisibility(View.GONE);
			txtPercent.setVisibility(View.VISIBLE);
			buttonSpinner.setText("Show List");
			txtPercent.requestFocus();
		}
		else{
			spinnerPercent.setVisibility(View.VISIBLE);
			txtPercent.setVisibility(View.GONE);
			buttonSpinner.setText("Custom");
		}
		
		
	}
	
	/**
	 * Fill a spinner with int values
	 * 
	 * @param spinner
	 * @param minVal
	 * @param maxVal
	 */
	public void fillSpinner(Spinner spinner,int minVal, int maxVal){

		if(!(minVal>=0 && maxVal>=minVal)){
			return;
		}
		List<Integer> list=new ArrayList<Integer>();
		for(int i=minVal;i<=maxVal;i++){
			list.add(i);
		}
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, list);
		spinner.setAdapter(adapter);
	}
	
	/**
	 * Fill a spinner with int values
	 * 
	 * @param spinner
	 * @param minVal
	 * @param maxVal
	 * @param extra
	 */
	public void fillSpinner(Spinner spinner,int minVal, int maxVal, int extra){
		
		if(!(minVal>=0 && maxVal>=minVal)){
			return;
		}
		List<Integer> list=new ArrayList<Integer>();
        for(int i=minVal;i<=maxVal;i++){
        	list.add(i);
        }
        if(extra>0){
        	list.add(extra);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
	}
	
	
	protected void savePreferences(){
		// Create or retrieve the shared preference object.
		SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS,Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		
		float strVal=(Float)spinnerPercent.getSelectedItem();
		editor.putFloat("strPercent",strVal);
		editor.commit();
	}
	
	public void loadPreferences() {
		// Get the stored preferences
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS,Activity.MODE_PRIVATE);
		// Retrieve the saved values.
		float strPercent;
		strPercent = mySharedPreferences.getFloat("strPercent",0);
		//spinnerPercent.setSelection(Integer.parseInt(strPercentIndex), true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  super.onSaveInstanceState(savedInstanceState);
	  savedInstanceState.putString("txtPercentOuput", txtPercentOuput.getText().toString());
	  savedInstanceState.putString("txtTotal", txtTotal.getText().toString());
	  savedInstanceState.putString("txtSplittedAmount", txtSplittedAmount.getText().toString());	 
	  
	  savedInstanceState.putString("buttonSpinner",buttonSpinner.getText().toString());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);
	  
	  txtPercentOuput.setText(savedInstanceState.getString("txtPercentOuput"));
	  txtTotal.setText(savedInstanceState.getString("txtTotal"));
	  txtSplittedAmount.setText(savedInstanceState.getString("txtSplittedAmount"));
	  buttonSpinner.setText(savedInstanceState.getString("buttonSpinner"));
	  
	  if(!savedInstanceState.getString("buttonSpinner").toString().equalsIgnoreCase("Custom")){
		  spinnerPercent.setVisibility(View.GONE);
		  txtPercent.setVisibility(View.VISIBLE);
	  }
	}
	
	/*
	// Set an EditText view to get user input 
	final EditText input = new EditText(this);
	
	input.setInputType(InputType.TYPE_CLASS_NUMBER 
			| InputType.TYPE_NUMBER_FLAG_DECIMAL 
			| InputType.TYPE_NUMBER_FLAG_SIGNED);
	
	new AlertDialog.Builder(this)
    .setTitle("Enter Custom Tip Percent")
    //.setMessage("Message")
    .setView(input)
    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            Editable value = input.getText(); 
            fillSpinnerDouble(spinnerPercent, 0, 100, Double.parseDouble(value.toString()));
            spinnerPercent.setSelection(101);
            
        }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            // Do nothing.
        }
    }).show();
    */
	
	/*
	//This excute in case of orientation
	@Override
	public Object onRetainNonConfigurationInstance() {
		
		SharedPreferences mySharedPreferences = getSharedPreferences(MyOrientationPref,Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putBoolean("fromOrient", true);
		editor.commit();
		
		return null;
	}
	
	@Override
	protected void onPause() {
		if(fromOrientation)
		{
			SharedPreferences mySharedPreferences = getSharedPreferences(MyOrientationPref,Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putBoolean("fromOrient", true);
			editor.commit();
		}
		super.onPause();
	}
	*/


}
