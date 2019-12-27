package io.github.ramonsantos.expensesnotebook

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.ramonsantos.expensesnotebook.config.AppDatabase
import io.github.ramonsantos.expensesnotebook.dao.ExpenseDao
import io.github.ramonsantos.expensesnotebook.ui.ExpenseFormActivity
import io.github.ramonsantos.expensesnotebook.ui.ExpenseListAdapter
import io.github.ramonsantos.expensesnotebook.ui.SettingsActivity
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

        recyclerViewExpense.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        fab.setOnClickListener { view ->
            val intent = Intent(this, ExpenseFormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        recyclerViewExpense.adapter = ExpenseListAdapter(expenseDao.getAll())
        super.onResume()
    }
}
