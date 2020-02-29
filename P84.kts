/*
* Giancarlo Marte Peralta - A01702312
* Kotlin problem #84 -  Minimum spanning tree
 */


/////////////////////////////////Algorithm/////////////////////////////////////////
/*
* The minimum spanning tree of a connected undirected and weighted graph is a tree
* such that it still connects all of the vertices, but the sum of the weights is
* minimal, and since it's a tree, there are no cycles. Prim's algorithm is one of the
* easiest ways to solve this problem. It's a greedy approach where we start to form
* the tree node by node. We start with an arbitrary node and add it, then at every
* step we add the node that cost the less to add, until al of them are added. One way
* of implementing would be like this:
*
* 1.Set a cost of infinity to all nodes but one (in this case infinity = 1000000000)
* 2.Set a cost of 0 to the left out node
* 3.Crete a list, remaining_nodes, initially has all nodes
* 4.Until remaining_nodes is empty do the following:
*   4.1 Pick a node, w, with the minimum cost
*   4.2 Delete the node from remaining_nodes
*   4.3 If this is not the first deleted node
*       4.3.1 Record it as part of the answer together with the last node who updated it
*   4.4 Update the costs of all adjacent nodes (if current cost is more than the weight of the path, set it to said weight)
*   4.5 Record the label w, as the last node that updated the nodes that changed cost
* 5.Print the results
*
* P.S: The github test cases have unweighted cases that here are treated as having weight 1.
 */

var adj_list = mutableMapOf<String, MutableList<Pair<String, Int>>>();
var cost = mutableMapOf<String, Int>();
var remaining = mutableListOf<String>();
var answer = mutableListOf<String>();
var who_added_me = mutableMapOf<String, String>();

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
        var len:Int = 0;
        var idx:Int = paths[i].indexOf("-");
        var idx2:Int = paths[i].indexOf("/");
        when(idx){
            -1 -> {
                node1 = paths[i];
                if(!adj_list.containsKey(node1)){
                    adj_list.put(key = node1, value = mutableListOf<Pair<String, Int>>())
                }
            }
            else -> {
                node1 = paths[i].substring(startIndex = 0, endIndex = idx);
                node2 = paths[i].substring(startIndex = idx+1, endIndex =idx2);
                len = paths[i].substring(startIndex = idx2+1).toInt();
                if(adj_list.containsKey(node1)){
                    adj_list[node1]?.add(Pair(node2, len));
                }
                else{
                    adj_list[node1] = mutableListOf<Pair<String, Int>>(Pair(node2, len));
                }
                if(adj_list.containsKey(node2)){
                    adj_list[node2]?.add(Pair(node1, len));
                }
                else{
                    adj_list[node2] = mutableListOf<Pair<String, Int>>(Pair(node1, len));
                }
            }
        }

    }
    /////////////////////////////Fill visited nodes////////////////////////////////////////
    for(i in 0..adj_list.keys.size-1){
        if(i == 0)
            cost[adj_list.keys.elementAt(i)] = 0;
        else
            cost[adj_list.keys.elementAt(i)] = 1000000000;
        remaining.add(adj_list.keys.elementAt(i));
    }
}

fun primAlgo(){
    var flag = false;
    while(remaining.size > 0) {
        var min_val: Int = 1000000000;
        var min_key: String = "non";
        for(i in 0..remaining.size-1){
            if(cost.get(remaining[i])!! < min_val) {
                min_val = cost[remaining[i]]!!;
                min_key = remaining[i];
            }
        }
        if(flag) answer.add(who_added_me[min_key] + "-" + min_key + "/" + min_val.toString());
        remaining.remove(min_key);
        ////////////////////////////////////update the cost////////////////////////////
        for(i in 0..(adj_list[min_key]?.size ?: -1)){
            if(i == adj_list[min_key]?.size ?: -1) continue;
            if(cost[adj_list[min_key]?.get(i)?.first]!! > adj_list[min_key]?.get(i)?.second!!){
                cost[adj_list[min_key]!!.get(i).first] = adj_list[min_key]!!.get(i).second;
                who_added_me[adj_list[min_key]!!.get(i).first] = min_key;
            }
        }
        flag = true;
    }
    println(answer);
}

fun main(){
    ///////////////////////////////Test case 1////////////////////////////////////////////////
    println("Test case 1:");
    createGraph("[a-b/1]");
    primAlgo();
    adj_list = mutableMapOf<String, MutableList<Pair<String, Int>>>();
    cost = mutableMapOf<String, Int>();
    remaining = mutableListOf<String>();
    answer = mutableListOf<String>();
    who_added_me = mutableMapOf<String, String>();
    ///////////////////////////////Test case 2////////////////////////////////////////////////
    println("Test case 2:");
    createGraph("[a-b/1, b-c/2]");
    primAlgo();
    adj_list = mutableMapOf<String, MutableList<Pair<String, Int>>>();
    cost = mutableMapOf<String, Int>();
    remaining = mutableListOf<String>();
    answer = mutableListOf<String>();
    who_added_me = mutableMapOf<String, String>();
    ///////////////////////////////Test case 3////////////////////////////////////////////////
    println("Test case 3:");
    createGraph("[a-b/1, b-c/2, a-c/3]");
    primAlgo();
    adj_list = mutableMapOf<String, MutableList<Pair<String, Int>>>();
    cost = mutableMapOf<String, Int>();
    remaining = mutableListOf<String>();
    answer = mutableListOf<String>();
    who_added_me = mutableMapOf<String, String>();
    ///////////////////////////////Test case 4////////////////////////////////////////////////
    println("Test case 4:");
    createGraph("[a-b/1]");
    primAlgo();
    adj_list = mutableMapOf<String, MutableList<Pair<String, Int>>>();
    cost = mutableMapOf<String, Int>();
    remaining = mutableListOf<String>();
    answer = mutableListOf<String>();
    who_added_me = mutableMapOf<String, String>();
    ///////////////////////////////Test case 5////////////////////////////////////////////////
    println("Test case 5:");
    createGraph("[a-b/1, b-c/1]");
    primAlgo();
    adj_list = mutableMapOf<String, MutableList<Pair<String, Int>>>();
    cost = mutableMapOf<String, Int>();
    remaining = mutableListOf<String>();
    answer = mutableListOf<String>();
    who_added_me = mutableMapOf<String, String>();
    ///////////////////////////////Test case 6////////////////////////////////////////////////
    println("Test case 6:");
    createGraph("[a-b/1, b-c/1, a-c/1]");
    primAlgo();
    adj_list = mutableMapOf<String, MutableList<Pair<String, Int>>>();
    cost = mutableMapOf<String, Int>();
    remaining = mutableListOf<String>();
    answer = mutableListOf<String>();
    who_added_me = mutableMapOf<String, String>();
    ///////////////////////////////Test case 7////////////////////////////////////////////////
    println("Test case 7:");
    createGraph("[a-b/5, a-d/3, b-c/2, b-e/4, c-e/6, d-e/7, d-f/4, d-g/3, e-h/5, f-g/4, g-h/1]");
    primAlgo();
    adj_list = mutableMapOf<String, MutableList<Pair<String, Int>>>();
    cost = mutableMapOf<String, Int>();
    remaining = mutableListOf<String>();
    answer = mutableListOf<String>();
    who_added_me = mutableMapOf<String, String>();
    ///////////////////////////////Test cases added by me/////////////////////////////////////
    ///////////////////////////////Test case 8////////////////////////////////////////////////
    println("Test case 8:");
    createGraph("[a-c/30, c-d/50, b-d/29, a-b/5]");
    primAlgo();
    adj_list = mutableMapOf<String, MutableList<Pair<String, Int>>>();
    cost = mutableMapOf<String, Int>();
    remaining = mutableListOf<String>();
    answer = mutableListOf<String>();
    who_added_me = mutableMapOf<String, String>();
    ///////////////////////////////Test case 9////////////////////////////////////////////////
    println("Test case 9:");
    createGraph("[a-c/30, c-d/50, b-d/29, a-b/5, a-d/1, c-b/12]");
    primAlgo();
    adj_list = mutableMapOf<String, MutableList<Pair<String, Int>>>();
    cost = mutableMapOf<String, Int>();
    remaining = mutableListOf<String>();
    answer = mutableListOf<String>();
    who_added_me = mutableMapOf<String, String>();
}