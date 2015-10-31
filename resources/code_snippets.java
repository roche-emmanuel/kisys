
// Code used to check the paths of the asset data:
/* try listing the asset content: */
final AssetManager assets = getAssets();
try {
   final String[] names = assets.list( "/" );

   // List all the assets:
   int num = names.length;
   for(int i=0;i<num;++i) {
       Log.d(TAG,"Found asset path: "+names[i]);
   }
   Log.d(TAG, "Done listing asset paths.");
} catch (IOException e) {
   e.printStackTrace();
}