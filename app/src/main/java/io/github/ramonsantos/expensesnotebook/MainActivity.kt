package io.github.ramonsantos.expensesnotebook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.ramonsantos.expensesnotebook.config.AppDatabase
import io.github.ramonsantos.expensesnotebook.dao.ExpenseDao
import io.github.ramonsantos.expensesnotebook.ui.ExpenseFormActivity
import io.github.ramonsantos.expensesnotebook.ui.ExpenseListAdapter

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var appDatabase: AppDatabase
    private lateinit var expenseDao: ExpenseDao
    private lateinit var recyclerViewExpense: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        appDatabase = AppDatabase.getInstance(applicationContext)
        expenseDao = appDatabase.expenseDao()

        viewManager = LinearLayoutManager(this)
        viewAdapter = ExpenseListAdapter(appDatabase.expenseDao().getAll())

        recyclerViewExpense = findViewById<RecyclerView>(R.id.recycler_view_expense).apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        fab.setOnClickListener { view ->
            val intent = Intent(this, ExpenseFormActivity::class.java).apply {
                putExtra("TESTE", "teste")
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        recyclerViewExpense.adapter = ExpenseListAdapter(expenseDao.getAll())
        super.onResume()
    }
}
