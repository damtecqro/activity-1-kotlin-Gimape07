/*
* Giancarlo Marte Peralta - A01702312
* Kotlin problem #88 -  Connected components
 */

/////////////////////////////////Algorithm/////////////////////////////////////////
/*
* Although the code seems long, must of it is just creating an adjacency list, given
* the input format that the graphs are given into. Connected components on an
* undirected graph can be find by simply doing a bfs or dfs, it is as follows:
*
* 1.Start with any node
* 2.Check that it has not been visited
*   2.1 If it has been visited, go on to the next node and back to step 2
*       2.1.1 If there's not next node, you're done
* 3.Put the node on a list and mark it as visited
* 4.Recursively repeat step 3 for every non visited adjacent node
* 5.When there are no more nodes to visit, print the node list
* 6.Empty the node list
* 7.Go to the next node and back to step 2
*   7.1 If there's no next node, you're done
 */


var adj_list = mutableMapOf<String, MutableList<String>>();
var visited = mutableMapOf<String, Int>();
var component = mutableListOf<String>();

fun createGraph(graph:String){
    ///////////////////////////////Clean de input///////////////////////////////////////////////
    var graph_clean:String = graph.replace(" ", "");
    graph_clean = graph_clean.replace("[", "");
    graph_clean = graph_clean.replace("]", "");
    var paths = mutableListOf<String>();
    var start:Int = 0;
    ///////////////////////////////Separate all of the paths////////////////////////////////////
    while(true) {
        var idx:Int = graph_clean.indexOf(",", startIndex = start);
        if (idx == -1) {
            paths.add(graph_clean.substring(startIndex = start))
            break;
        }
        paths.add(graph_clean.substring(startIndex = start, endIndex = idx))
        start = idx + 1;
    }
    ///////////////////////////////Create adjacency list//////////////////////////////////////
    for(i in 0..paths.size-1){
        var node1:String? = null;
        var node2:String? = null;
        var idx:Int = paths[i].indexOf("-");
        when(idx){
            -1 -> {
                node1 = paths[i];
                if(!adj_list.containsKey(node1)){
                    adj_list.put(key = node1, value = mutableListOf<String>())
                }
            }
            else -> {
                node1 = paths[i].substring(startIndex = 0, endIndex = idx);
                node2 = paths[i].substring(startIndex = idx+1);
                if(adj_list.containsKey(node1)){
                    adj_list[node1]?.add(node2);
                }
                else{
                    adj_list[node1] = mutableListOf<String>(node2);
                }
                if(adj_list.containsKey(node2)){
                    adj_list[node2]?.add(node1);
                }
                else{
                    adj_list[node2] = mutableListOf<String>(node1);
                }
            }
        }

    }
    /////////////////////////////Fill visited nodes////////////////////////////////////////
    for(i in 0..adj_list.keys.size-1){
        visited[adj_list.keys.elementAt(i)] = 0;
    }
}

fun dfs(v:String){
    visited[v] = 1;
    component.add(v);
    for (i in 0..(adj_list[v]?.size ?: -1)){
        if(i == adj_list[v]?.size ?: -1) continue;
        if(visited[adj_list[v]?.get(i)] == 0){
            var x:String = adj_list[v]?.get(i) ?: "non";
            dfs(x);
        }
    }

}
fun connectedComponenets(){
    for(i in 0..adj_list.keys.size-1){
        if(visited[adj_list.keys.elementAt(i)] == 0){
            dfs(adj_list.keys.elementAt(i));
            println(component);
            component = mutableListOf();
        }
    }
}
fun main(){
    ///////////////////////////////Test case 1////////////////////////////////////////////////
    println("Test case 1:");
    createGraph("[a-b]");
    connectedComponenets();
    adj_list = mutableMapOf<String, MutableList<String>>();
    visited = mutableMapOf<String, Int>();
    ///////////////////////////////Test case 2////////////////////////////////////////////////
    println("Test case 2:");
    createGraph("[a-b, c-d]");
    connectedComponenets();
    adj_list = mutableMapOf<String, MutableList<String>>();
    visited = mutableMapOf<String, Int>();
    ///////////////////////////////Test cases added by me/////////////////////////////////////
    ///////////////////////////////Test case 3////////////////////////////////////////////////
    println("Test case 3:");
    createGraph("[a-b, c-d, e]");
    connectedComponenets();
    adj_list = mutableMapOf<String, MutableList<String>>();
    visited = mutableMapOf<String, Int>();
    ///////////////////////////////Test case 4////////////////////////////////////////////////
    println("Test case 4:");
    createGraph("[a-b, c-d, e, g-a, g-b, g-d]");
    connectedComponenets();
    adj_list = mutableMapOf<String, MutableList<String>>();
    visited = mutableMapOf<String, Int>();
    ///////////////////////////////Test case 5////////////////////////////////////////////////
    println("Test case 5:");
    createGraph("[mexico-usa, usa-canada, australia, alemania-francia, francia-espania, rusia-china, " +
            "china-india, japon, nigeria-chad, madagascar]");
    connectedComponenets();
    adj_list = mutableMapOf<String, MutableList<String>>();
    visited = mutableMapOf<String, Int>();
}