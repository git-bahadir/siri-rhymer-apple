# Siri Rhymer

Matches ending prefix of input word against a list of words txt. Returns words with maximum ending prefix match, basically rhyming. 

## Data Structure

Initial idea was to to use a balanced binary tree to store letters for prefixes however, since we can afford to lose time on insertion but need fast lookup I went with using a Trie.

Being inspired by the trie structure https://en.wikipedia.org/wiki/Trie used in autocorect I set out to store the word in trie nodes. However, a downside to using tries is that all letters of every word takes a single node by itself. This poses a slow down and memory increase in cases where long words that do not contain matching character orders with other words thus cannot be mapped to the same node will have individual nodes. To oppose that I created a compressed trie structure known as a Radix Tree.


## Inner workings of my tree

My Radix Tree tree as opposed to a standard trie structure assures that leaf nodes do not extend beyond the matching characters by putting the leftover characters to a single string rather than creating nodes for each of them.

Each child is stored within a Hashmap structure that allows for fast retrieval and is only created when a child is guaranteed to be added to that node thus saving memory. 

Compressed property of the tree is always ensured (it isn't compressed after creation) by breakuing up leaf nodes and distributing their matching characters whenever a viable node is added.

Since we are searching for rhymes each word added and searched in the tree is reversed for more efficient lookup.

After tree creation is completed, when searching for a prefix match our tree visits nodes and their children that match the prefix characters at indexes. Traversal stops at the last matching character's node and every child coming after that node is returned as a match.


## Space and Time Complexity of operations

### Insertion
     Total Space complexity: O(N*M) where N is the number of words and M is the average size. We save space here with respect to regular trie because we keep our tree compressed and also don't initiliaze hashmap unless a new child is added.

     Time complexity: O(N*M) which is the same as Trie. Since we have N words to insert and for each letter we check if we need to breakup about M times.
###

### Search
    Time complexity: O(M + K) with M being average size of word since we hop from node to node directly since each child node is stored within a hashmap and K being the size of the prefix. Because the first traversal part is O(K) as we hop K elements and after we find the children to retrieve we hop another M times to retrieve children of them. This step could be improved to O(K) if every child also stored the list of their children which would significantly increase memory. If I had more time I would probably have done this since fast search is vital when using a voice recognition engine as opposed to storage.
###

## How much effort it took

It took me 4.5 hours to build the Radix Tree, I beleive that if I went with a simple Trie it could have taken 3.5 hours because of the bottleneck of breaking up nodes.

Implementing tests and this document took me an additional 1 hour.

## Building and running test

Run `mvn clean install` to run maven to install dependencies and build.

Then you can run the test file for the scenarios I built.

## Running individual main file

I also provided with an individual (SiriRhyme.java under src/main) main file that can be run by giving an input for txt file of words and a string to search prefix.
### example
    baha@bahas-MacBook-Pro siri-rhymer-apple %  /usr/bin/env /Users/baha/.sdkman/candidates/java/current/bin/java -Dfile.encoding=UTF-8 -cp /Users/baha/Documents/work/coding_challenge/test/siri-rhymer-apple/target/classes com.bahadir.SiriRhyme /Users/baha/Documents/work/coding_challenge/test/siri-rhymer-apple/src/test/java/com/bahadir/resources/testData/fullTree/test.txt content
    content
    discontent
    malcontent
    miscontent
    noncontent
    precontent
    prediscontent
    uncontent
    wellcontent
###
