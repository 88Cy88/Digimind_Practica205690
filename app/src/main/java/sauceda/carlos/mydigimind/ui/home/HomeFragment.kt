package sauceda.carlos.mydigimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import sauceda.carlos.mydigimind.R
import sauceda.carlos.mydigimind.databinding.FragmentHomeBinding
import sauceda.carlos.mydigimind.ui.Task

class HomeFragment : Fragment() {
    var tasks = ArrayList<Task>()
    private var adaptador: AdoptadorTareas?=null
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        fillTask()

        adaptador = AdoptadorTareas(root.context,tasks)

        val gridView: GridView = root.findViewById(R.id.reminders)
        gridView.adapter = adaptador
        return root
    }

    fun fillTask(){
        tasks.add(Task("Practice 1", arrayListOf("Tuesday"),"17:30"))
        tasks.add(Task("Practice 2", arrayListOf("Monday","Sunday"),"17:40"))
        tasks.add(Task("Practice 3", arrayListOf("Monday","Sunday"),"14:00"))
        tasks.add(Task("Practice 4", arrayListOf("Monday","Sunday"),"11:00"))
        tasks.add(Task("Practice 5", arrayListOf("Monday","Sunday"),"13:00"))
        tasks.add(Task("Practice 6", arrayListOf("Monday","Sunday"),"10:40"))
        tasks.add(Task("Practice 7", arrayListOf("Monday","Sunday"),"12:00"))
    }

    private class AdoptadorTareas: BaseAdapter{
        var tasks=ArrayList<Task>()
        var contexto: Context?= null

        constructor(context: Context, task:ArrayList<Task>){
            this.contexto = contexto
            this.tasks=tasks
        }

        override fun getCount(): Int {
            return tasks.size
        }

        override fun getItem(p0: Int): Any {
            return tasks[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var task = tasks[p0]
            var inflador = LayoutInflater.from(contexto)
            var vista = inflador.inflate(R.layout.task_view,null)

            var tv_title: TextView = vista.findViewById(R.id.tv_title)
            var tv_time: TextView = vista.findViewById(R.id.tv_time)
            var tv_days: TextView = vista.findViewById(R.id.tv_day)

            tv_title.setText(task.title)
            tv_title.setText(task.timepo)
            tv_days.setText(task.days.toString())

            return vista
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}