package medicinas;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.NPI.yayo.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

import medicinas.Model.ToDoModel;
import medicinas.Utils.DatabaseHandler;

public class AddNewMedicine extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newMedicineText;
    private TimePicker newMedicineHour;
    private Button newMedicineSaveButton;

    private DatabaseHandler db;

    public static AddNewMedicine newInstance(){
        return new AddNewMedicine();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_medicina, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newMedicineText = requireView().findViewById(R.id.newTaskText);
        newMedicineHour = requireView().findViewById(R.id.hora);
        newMedicineSaveButton = getView().findViewById(R.id.newTaskButton);

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newMedicineText.setText(task);
            assert task != null;
            if(task.length()>0)
                newMedicineSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        newMedicineText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newMedicineSaveButton.setEnabled(false);
                    newMedicineSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newMedicineSaveButton.setEnabled(true);
                    newMedicineSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newMedicineSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newMedicineHour.getHour()+":"+ newMedicineHour.getMinute()+" - "+ newMedicineText.getText().toString();
                if(finalIsUpdate){
                    db.updateTask(bundle.getInt("id"), text);
                }
                else {
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setStatus(0);
                    db.insertTask(task);

                    //alarma
                    Calendar calendar = Calendar.getInstance();
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                    intent.putExtra(AlarmClock.EXTRA_HOUR, newMedicineHour.getHour());
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, newMedicineHour.getMinute());
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, newMedicineText.getText().toString());
                    ArrayList<Integer> alarmDays= new ArrayList<Integer>();
                    alarmDays.add(Calendar.SUNDAY);
                    alarmDays.add(Calendar.MONDAY);
                    alarmDays.add(Calendar.TUESDAY);
                    alarmDays.add(Calendar.WEDNESDAY);
                    alarmDays.add(Calendar.THURSDAY);
                    alarmDays.add(Calendar.FRIDAY);
                    alarmDays.add(Calendar.SATURDAY);
                    intent.putExtra(AlarmClock.EXTRA_DAYS, alarmDays);
                    startActivity(intent);

                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);
    }
}
