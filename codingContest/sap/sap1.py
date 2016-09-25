import sys


def combinationInLine(n):
    return (n*(n+1))/2

def printCount(lst):
    totalCount = 0
    # find total number of consecutive duplicates
    currSum=1
    currEle=lst[0]
    for i in range(1, len(lst)):
        if currEle == lst[i]:
            currSum=currSum+1
        else:

            numCombination=combinationInLine(currSum)
            totalCount= totalCount+numCombination
            currEle=lst[i]
            currSum=1

    numCombination = combinationInLine(currSum)
    totalCount = totalCount + numCombination
    print totalCount


if __name__ == '__main__':
    #for line in sys.stdin:
       # print line,
    numTestCases= int(raw_input())
    testcases=[]
    for i in range(0, numTestCases):
        currlen= input()
        currentTestCase = raw_input().split()
        currentTestCase = map(int, currentTestCase)
        if len(currentTestCase) != currlen:
            print 'error'
        testcases.append(currentTestCase)

    for i in range(0, numTestCases):
        printCount(testcases[i])