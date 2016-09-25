import sys
from sets import Set

def dfs(graph, start):
    visited, stack = Set(), [start]
    while stack:
        vertex = stack.pop()
        if vertex not in visited:
            visited.add(vertex)
            stack.extend(graph[vertex] - visited)
    return visited

def dfs_paths(graph, start, goal):
    stack = [(start, [start])]
    while stack:
        (vertex, path) = stack.pop()
        for next in graph[vertex] - Set(path):
            if next == goal:
                yield path + [next]
            else:
                stack.append((next, path + [next]))

def numUniqueElements(graph,nodeValues,a,b):
    lst=list(dfs_paths(graph, a, b))
    #print lst[0]
    tmpDict={}
    #print nodeValues
    for i in lst[0]:
        tmpDict[nodeValues[int(i)-1]]=i
    print len(tmpDict)

if __name__ == '__main__':
    #for line in sys.stdin:
       # print line,
    numNodes= int(raw_input())
    nodeValues = raw_input().split()
    nodeValues=map(int, nodeValues)
    adjList={}
    for i in range(0, numNodes-1):
        currentNodes = raw_input().split()
        nodeA=adjList.get(currentNodes[0], None)
        nodeB=adjList.get(currentNodes[1], None)

        #building graph
        if(nodeA != None):
            nodeA.add(currentNodes[1])
        else:
            elements = Set([])
            elements.add(currentNodes[1])
            adjList[currentNodes[0]] = elements

        if (nodeB != None):
            nodeB.add(currentNodes[0])
        else:
            elements = Set([])
            elements.add(currentNodes[0])
            adjList[currentNodes[1]] = elements

    Q = input()
    for i in range(0, Q):
        currentNodes = raw_input().split()
        numUniqueElements(adjList, nodeValues, currentNodes[0], currentNodes[1])
