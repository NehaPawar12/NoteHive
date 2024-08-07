package com.example.notehive.fragement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.notehive.MainActivity
import com.example.notehive.R
import com.example.notehive.databinding.FragmentAddNoteFragementBinding
import com.example.notehive.databinding.FragmentHomeFragementBinding
import com.example.notehive.model.Note
import com.example.notehive.viewmodel.NoteViewModel


class AddNoteFragement : Fragment(R.layout.fragment_add_note_fragement),MenuProvider {

    private var addNoteBinding: FragmentAddNoteFragementBinding?= null
    private val binding get()  = addNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var addNoteView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        addNoteBinding = FragmentAddNoteFragementBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel as NoteViewModel
        addNoteView = view
    }

    private fun saveNote(view:View){
        val noteTitle = binding.addNoteTitle.text.toString().trim()
        val noteDesc = binding.addNoteDesc.text.toString().trim()

        if(noteTitle.isNotEmpty()){
            val note = Note(0,noteTitle,noteDesc)
            notesViewModel.addNote(note)

            Toast.makeText(addNoteView.context, "Note Saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragement,false)
        }
        else{
            Toast.makeText(addNoteView.context, "Please enter note title", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_notes,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

        return when(menuItem.itemId){
            R.id.saveMenu ->{
                saveNote(addNoteView)
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }

}