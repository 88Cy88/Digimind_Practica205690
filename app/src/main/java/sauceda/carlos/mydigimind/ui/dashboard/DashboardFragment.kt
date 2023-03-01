package sauceda.carlos.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import sauceda.carlos.mydigimind.R
import sauceda.carlos.mydigimind.ui.Task
import sauceda.carlos.mydigimind.ui.home.HomeFragment
import sauceda.carlos.mydigimind.databinding.FragmentDashboardBinding
import java.text.SimpleDateFormat

import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard,container,false)

        val btn_time: Button = root.findViewById(R.id.btn_time)
        btn_time.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE, minute)
                btn_time.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(root.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

            root.btn_save.setOnClickListener {
            var titulo = et_task.text.toString()
            var hora = btn_time.text.toString()
            var dias= ArrayList<String>()

            if(checkMonday.isChecked)
                dias.add("Monday")
            if(checkTuesday.isChecked)
                dias.add("Tuesday")
            if(checkWednesday.isChecked)
                dias.add("Wednesday")
            if(checkThursday.isChecked)
                dias.add("Thursday")
            if(checkFriday.isChecked)
                dias.add("Friday")
            if(checkSaturday.isChecked)
                dias.add("Saturday")
            if(checkSunday.isChecked)
                dias.add("Sunday")

            var tarea= Actividad(titulo,dias,hora)
            HomeFragment.actividades.add(tarea)
            Toast.makeText(root.context,"New task added", Toast.LENGTH_SHORT).show()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}