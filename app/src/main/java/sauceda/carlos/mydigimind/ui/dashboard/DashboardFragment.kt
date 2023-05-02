package sauceda.carlos.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import sauceda.carlos.mydigimind.R
import sauceda.carlos.mydigimind.databinding.FragmentDashboardBinding
import java.text.SimpleDateFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var usuario: FirebaseAuth
    private lateinit var storage: FirebaseFirestore

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var dashboardViewModel: DashboardViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard,container,false)
        _binding = FragmentDashboardBinding.bind(root)
        // Inicializar Firebase
        //FirebaseApp.initializeApp(requireContext())
        // Instanciar Firebase
        storage = FirebaseFirestore.getInstance()
        usuario = FirebaseAuth.getInstance()

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

        val btn_save = root.findViewById(R.id.btn_save) as Button
        val et_titulo = root.findViewById(R.id.name) as EditText
        val checkMonday = root.findViewById(R.id.monday) as CheckBox
        val checkTuesday = root.findViewById(R.id.tuesday) as CheckBox
        val checkWednesday = root.findViewById(R.id.wednesday) as CheckBox
        val checkThursday = root.findViewById(R.id.thursday) as CheckBox
        val checkFriday = root.findViewById(R.id.friday) as CheckBox
        val checkSaturday = root.findViewById(R.id.saturday) as CheckBox
        val checkSunday = root.findViewById(R.id.sunday) as CheckBox

        btn_save.setOnClickListener{

            var actividad = hashMapOf(
                "actividad" to et_titulo.text.toString(),
                "lu" to checkMonday.isChecked,
                "ma" to checkTuesday.isChecked,
                "mi" to checkWednesday.isChecked,
                "ju" to checkThursday.isChecked,
                "vi" to checkFriday.isChecked,
                "sa" to checkSaturday.isChecked,
                "do" to checkSunday.isChecked,
                "tiempo" to btn_time.text.toString()
            )

            storage.collection("actividades")
                .add(actividad)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(root.context, "Task added successfully.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(root.context, "Error while adding task.", Toast.LENGTH_SHORT).show()
                }
        }
        return root
    }
}