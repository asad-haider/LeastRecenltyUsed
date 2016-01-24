package com.leastrecenltyused;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button calculateBtn;
    EditText stringEditText;
    EditText numberOfFramesEditText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.clear:
                stringEditText.setText("");
                numberOfFramesEditText.setText("");
                return true;
            case R.id.about:

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("About Project:");
                alertDialog.setMessage("Least Recently Used Paging Algorithm Implementation in Android Application\n" +
                        "We are highly indebted to Maam Sadia and Miss Farheen for their guidance and constant supervision as well as for providing " +
                        "necessary information regarding the project & also for their support in completing the project." +
                        "\n\n" +
                        "Project Created By: \n" +
                        "Syed Muhammad Asad Haider\t2014-SE-039\n" +
                        "Syed Sameer Kazmi\t2014-SE-035\n" +
                        "Muhammad Uzair Khan\t2014-SE-036\n" +
                        "Muhammad Umer\t2014-SE-54\n" +
                        "Hassan Siddiqui\t2014-SE-31");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.create().show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculateBtn = (Button) findViewById(R.id.calculateBtn);
        stringEditText = (EditText) findViewById(R.id.stringEditText);
        numberOfFramesEditText = (EditText) findViewById(R.id.numberOfFramesEditText);

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String numberString = String.valueOf(stringEditText.getText());
                    String strNumberOfFrames = String.valueOf(numberOfFramesEditText.getText());

                    if (numberString.equals("") || strNumberOfFrames.equals("")){
                        Toast.makeText(MainActivity.this, "Please fill All Fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int numberOfFrames = Integer.parseInt(strNumberOfFrames);

                        char[] numbersCharArray = numberString.toCharArray();

                        int[] numbers = new int[numbersCharArray.length];

                        for (int i = 0; i < numbersCharArray.length; i++) {
                            numbers[i] = (char) numbersCharArray[i] - 48;
                        }

                        int cacheHit = 0;

                        ArrayList<Integer> allTables = new ArrayList<>();

                        ArrayList<String> faultOrHit = new ArrayList<>();

                        int table[] = new int[numberOfFrames];

                        for (int i = 0; i < numberOfFrames; i++) {
                            table[i] = (-1);
                        }

                        ArrayList<Integer> recentlyUsed = new ArrayList<>();

                        table[0] = numbers[0];
                        saveTable(table, allTables);
                        recentlyUsed.add(numbers[0]);
                        faultOrHit.add("F");

                        for (int i = 1; i < numbers.length; i++) {
                            int tempNumber = numbers[i];

                            int[] lastTable = getLastTable(numberOfFrames, allTables);
                            boolean numberExistInTable = false;

                            for (int j = 0; j < lastTable.length; j++) {
                                if (tempNumber == lastTable[j]) {
                                    numberExistInTable = true;
                                    cacheHit++;
                                    faultOrHit.add("H");
                                }
                            }

                            boolean minusOneExists = false;
                            int minusOneIndex = 0;

                            if (numberExistInTable == false) {

                                for (int j = 0; j < lastTable.length; j++) {
                                    if (lastTable[j] == (-1)) {
                                        minusOneExists = true;
                                        minusOneIndex = j;
                                        break;
                                    }
                                }

                                if (minusOneExists) {
                                    lastTable[minusOneIndex] = tempNumber;
                                    faultOrHit.add("F");
                                } else {
                                    int numberToChange = getLeastRecentlyUsed(recentlyUsed, lastTable);

                                    for (int j = 0; j < lastTable.length; j++) {
                                        if (lastTable[j] == numberToChange) {
                                            lastTable[j] = tempNumber;
                                            faultOrHit.add("F");
                                        }
                                    }

                                }
                            }

                            saveTable(lastTable, allTables);
                            recentlyUsed.add(tempNumber);

                        }

                String frames = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<style>\n" +
                        "table {\n" +
                        "    width:100%;\n" +
                        "\n" +
                        "}\n" +
                        "table, th, td {\n" +
                        "    border: 1px solid black;\n" +
                        "    border-collapse: collapse;\n" +
                        "    font-size: 20px;\n" +
                        "    font-family: arial\n" +
                        "}\n" +
                        "th, td {\n" +
                        "    padding: 15px;\n" +
                        "    text-align: center;\n" +
                        "    color: #ffffff\n" +
                        "\n" +
                        "}\n" +
                        "table#t01 tr:nth-child(even) {\n" +
                        "    background-color: #80D8FF;\n" +
                        "}\n" +
                        "table#t01 tr:nth-child(odd) {\n" +
                        "   background-color:#00B0FF;\n" +
                        "}\n" +
                        "</style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<table id=\"t01\">";

                        for (int i = 0; i < numberOfFrames; i++) {

                            frames += "<tr>";

                            for (int j = i; j < allTables.size(); j += numberOfFrames) {

                                if (allTables.get(j) == -1) {
                                    frames += "<td>   </td>";


                                } else {
                                    frames += "<td> " + allTables.get(j) + " </td>";

                                }
                            }

                        frames += "</tr>";
                        }

                        frames += "<tr>";
                        for (int i = 0; i < faultOrHit.size(); i++) {
                            frames += "<td> " + faultOrHit.get(i) + " </td>";
                        }
                        frames += "</tr>";

                frames += "</table>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>\n";

                        Intent toResultAc = new Intent(MainActivity.this, ResultActivity.class);

                        toResultAc.putExtra("Frames", frames);
                        toResultAc.putExtra("Cache Hit", cacheHit);
                        toResultAc.putExtra("Page Fault", numbers.length - cacheHit);

                        startActivity(toResultAc);

                    }
                }
        });

    }

    int getLeastRecentlyUsed(ArrayList<Integer> recent, int[] lastTable){

        int[] indexes = new int[lastTable.length];

        for (int i = 0; i < lastTable.length; i++) {
            indexes[i] = recent.lastIndexOf(lastTable[i]);
        }

        Arrays.sort(indexes);

        int leastUsedNumber = recent.get(indexes[0]);

        return leastUsedNumber;

    }

    int[] getLastTable(int numberOfFrames, ArrayList<Integer> allTables){

        int[] lastTable = new int[numberOfFrames];

        int counter = 0;

        for (int i = allTables.size() - numberOfFrames; i < allTables.size(); i++) {
            lastTable[counter++] = allTables.get(i);
        }

        return lastTable;
    }

    void saveTable(int[] table, ArrayList<Integer> allTables){
        for (int i = 0; i < table.length; i++) {
            allTables.add(table[i]);
        }
    }
}
