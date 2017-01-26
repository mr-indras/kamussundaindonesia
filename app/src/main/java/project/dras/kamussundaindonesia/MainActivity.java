package project.dras.kamussundaindonesia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private PagerAdapter mPagerAdapter;
    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initialsie the pager
        this.initialisePaging();
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, AngkaFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, KeluargaFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, WarnaFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, PrasaFragment.class.getName()));
        this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        ViewPager pager = (ViewPager)super.findViewById(R.id.pager);
        pager.setAdapter(this.mPagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            //Toast.makeText(getApplicationContext(),"Aksi Tentang",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Informasi.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
