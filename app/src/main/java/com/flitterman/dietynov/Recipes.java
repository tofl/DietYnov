package com.flitterman.dietynov;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recipes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private RecipesAdapter myAdapter;
    //private RecipesAdapter adapter;
    private ArrayList<Recipe> recipesList = new ArrayList<Recipe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ListView
        listView = findViewById(R.id.recipesListView);

        // CLICK LISTENER
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("test", "test");
            }
        });


        String url = "http://dev.audreybron.fr/flux/flux_recettes.json";
        new GetRecipes().execute(url);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recipes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all_recipes) {
            //...
        } else if (id == R.id.home) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public class GetRecipes extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            HttpHandler sh = new HttpHandler();
            String jsonString = sh.makeServiceCall(urls[0]);

            if (jsonString != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONArray result = jsonObj.getJSONArray("result");

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject c = result.getJSONObject(i);
                        Recipe recipe = new Recipe();

                        String title = c.getString("title");
                        int portions = c.getInt("portions");
                        String url = c.getString("picture_url");

                        JSONObject time = c.getJSONObject("time");
                        int totalTime = time.getInt("total");
                        int prepTime = time.getInt("prep");
                        int bakingTime = time.getInt("baking");

                        JSONObject nutrition = c.getJSONObject("nutrition");
                        float kcal = Float.valueOf(nutrition.getString("kcal"));
                        float protein = Float.valueOf(nutrition.getString("protein"));
                        float fat = Float.valueOf(nutrition.getString("fat"));
                        float carbohydrate = Float.valueOf(nutrition.getString("carbohydrate"));
                        float sugar = Float.valueOf(nutrition.getString("sugar"));
                        float sat_fat = Float.valueOf(nutrition.getString("sat_fat"));
                        float fiber = Float.valueOf(nutrition.getString("fiber"));
                        float sodium = Float.valueOf(nutrition.getString("sodium"));


                        JSONArray ingredients = c.getJSONArray("ingredients");
                        for (int j = 0; j < ingredients.length(); j++) {
                            JSONObject ing = ingredients.getJSONObject(j);
                            int quantity = ing.getInt("quantity");
                            String unit = ing.getString("unit");
                            String name = ing.getString("name");

                            Ingredients ingredient = new Ingredients();
                            ingredient.setQuantity(quantity);
                            ingredient.setUnit(unit);
                            ingredient.setName(name);

                            recipe.addIngredient(ingredient);
                        }


                        JSONArray steps = c.getJSONArray("steps");
                        for (int j = 0; j < steps.length(); j++) {
                            JSONObject st = steps.getJSONObject(j);
                            int order = st.getInt("order");
                            String stepDescription = st.getString("step");

                            Steps step = new Steps();
                            step.setOrder(order);
                            step.setStep(stepDescription);

                            recipe.addStep(step);
                        }


                        recipe.setTitle(title);
                        recipe.setPortions(portions);
                        recipe.setPictureUrl(url);
                        recipe.setTotalTime(totalTime);
                        recipe.setPrepTime(prepTime);
                        recipe.setBakingTime(bakingTime);
                        recipe.setKcal(kcal);
                        recipe.setProtein(protein);
                        recipe.setFat(fat);
                        recipe.setCarbohydrate(carbohydrate);
                        recipe.setSugar(sugar);
                        recipe.setSat_fat(sat_fat);
                        recipe.setFiber(fiber);
                        recipe.setSodium(sodium);

                        recipesList.add(recipe);
                    }
                } catch (Exception e) {
                    Log.e("Ynov", "There was an error : " + e.getMessage());
                }
            } else {
                Log.e("Ynov", "La connexion au serveur n'a pas pu être établie");
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Update the list :
            myAdapter = new RecipesAdapter(Recipes.this, recipesList);
            myAdapter.addAll(recipesList);
            listView.setAdapter(myAdapter);
        }

    }

    public class RecipesAdapter extends ArrayAdapter<Recipe> {

        public RecipesAdapter(Context context, List<Recipe> recipes) {
            super(context, 0, recipes);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Recipe recipe = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
            }

            TextView name = convertView.findViewById(R.id.line);
            name.setText(recipe.getTitle());

            return convertView;
        }

        public void destroy(View v) {
            finish();
        }

    }




    /*
    public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

        private ArrayList<Recipe> mDataset;



        public class RecipesViewHolder extends RecyclerView.ViewHolder {
            public TextView nameTextView;
            private View.OnClickListener onItemClickListener;

            public void setItemClickListener(View.OnClickListener clickListener) {
                onItemClickListener = clickListener;
            }
            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public RecipesViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                nameTextView = (TextView) itemView.findViewById(R.id.line);

                itemView.setTag(this);
            }
        }


        // Provide a suitable constructor (depends on the kind of dataset)
        public RecipesAdapter(ArrayList<Recipe> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RecipesAdapter.RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.row_layout, parent, false);

            RecipesViewHolder vh = new RecipesViewHolder(contactView);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(RecipesViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            Recipe recipe = (Recipe) mDataset.get(position);
            // Get the data model based on position

            // Set item views based on your views and data model
            TextView textView = holder.nameTextView;
            textView.setText(recipe.getTitle());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

    }
    */

}

