package com.example.todoapp.todo.add

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityTodoBinding
import com.example.todoapp.todo.home.TodoModel

class TodoContentActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MODEL = "extra_model"
        private const val EXTRA_TODO_CONTENT_TYPE = "extra_todo_content_type"
        fun newIntentForAdd(context: Context): Intent {
            return Intent(context, TodoContentActivity::class.java).apply {
                putExtra(EXTRA_TODO_CONTENT_TYPE, TodoContentType.ADD)
            }
        }
        fun newIntentForEdit(requireActivity: FragmentActivity?, todoModel: TodoModel): Intent {
            return Intent(requireActivity, TodoContentActivity::class.java).apply {
                putExtra(EXTRA_MODEL, todoModel)
                putExtra(EXTRA_TODO_CONTENT_TYPE, TodoContentType.EDIT)
            }
        }
    }

    private lateinit var binding: ActivityTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolBar.title = getString(R.string.todo_toolbar)

        val todoContentType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_TODO_CONTENT_TYPE, TodoContentType::class.java)
        } else {
            intent.getSerializableExtra(EXTRA_TODO_CONTENT_TYPE)
        }

        when(todoContentType) {
            TodoContentType.ADD -> {
                btnSubmit.text = getString(R.string.todo_submit_btn_add)
            }
            TodoContentType.EDIT -> {
                btnSubmit.text = getString(R.string.todo_submit_btn_edit)
                val editTodoModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(TodoContentActivity.EXTRA_MODEL, TodoModel::class.java)
                } else {
                    intent.getParcelableExtra(TodoContentActivity.EXTRA_MODEL)
                }
                edtTitle.setText(editTodoModel?.title)
                edtContent.setText(editTodoModel?.content)
            }
        }

        btnSubmit.setOnClickListener {
            val todoIntent = Intent().apply {
                putExtra(EXTRA_MODEL, TodoModel(edtTitle.text.toString(), edtContent.text.toString()))
            }
            setResult(RESULT_OK, todoIntent)
            if(!isFinishing) {
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                //toolBar 뒤로가기 버튼 클릭 시
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}