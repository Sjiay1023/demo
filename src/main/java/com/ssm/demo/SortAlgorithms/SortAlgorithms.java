package com.ssm.demo.SortAlgorithms;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Stack;

/**
 * 八大排序算法实现
 *
 * 1. 直接插入排序  {@link #insertionSort}
 * 2. 希尔排序      {@link /#shellSort}
 * 3. 简单选择排序  {@link #selectionSort}
 * 4. 堆排序        {@link /#heapSort}
 * 5. 冒泡排序      {@link #bubbleSort}
 * 6. 快速排序      {@link #quickSort}
 * 7. 归并排序      {@link #mergingSort}
 * 8. 基数排序      {@link /#radixSort}
 *
 * 算法测试类见 {@link /Test}
 * 算法评估分析类见 {@link /Bench}
 *
 */
public class SortAlgorithms {
    public static final boolean ENABLE_PRINT = true;

    public static void main(String[] args) {
       int[] array = new int[]{5, 3, 9, 1, 6, 4, 10, 2, 8, 7, 15, 3, 2};

        System.out.println("Before: " + Arrays.toString(array));
//          SortAlgorithms.insertionSort(array);
//        SortAlgorithms.shellSort(array);
//        SortAlgorithms.selectionSort(array);
//        SortAlgorithms.heapSort(array);
//        SortAlgorithms.bubbleSort(array);
//        SortAlgorithms.quickSort(array, 0, array.length-1);
//        SortAlgorithms.quickSortByStack(array);
        array = SortAlgorithms.mergingSort(array);
//        SortAlgorithms.radixSort(array);
        System.out.println("After:  " + Arrays.toString(array));
    }
    /**
     * 统一控制是否控制台输出
     */
    private static void System_out_println(String str){
        if(ENABLE_PRINT){
            System.out.println(str);
        }
    }

    /**
     * 直接插入排序
     *
     * 1. 从第一个元素开始，该元素可以认为已经被排序
     * 2. 取出下一个元素，在已经排序的元素序列中从后向前扫描
     * 3. 如果该元素（已排序）大于新元素，将该元素移到下一位置
     * 4. 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
     * 5. 将新元素插入到该位置后
     * 6. 重复步骤2~5
     * @param arr  待排序数组
     */
    public static void insertionSort(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            for(int j=i+1;j>0;j--){
                if(arr[j-1]<=arr[j])
                    break;
                int temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                System_out_println("Sorting: "+ Arrays.toString(arr));
            }
        }
    }

    /**
     * 选择排序
     *
     * 1. 从待排序序列中，找到关键字最小的元素；
     * 2. 如果最小元素不是待排序序列的第一个元素，将其和第一个元素互换；
     * 3. 从余下的 N - 1 个元素中，找出关键字最小的元素，重复①、②步，直到排序结束。
     *    仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
     * @param arr  待排序数组
     */
    public static void selectionSort(int[] arr){
        for(int i = 0; i < arr.length-1; i++){
            int min = i;
            for(int j = i+1; j < arr.length; j++){    //选出之后待排序中值最小的位置
                if(arr[j] < arr[min]){
                    min = j;
                }
            }
            if(min != i){
                int temp = arr[min];      //交换操作
                arr[min] = arr[i];
                arr[i] = temp;
                System_out_println("Sorting:  " + Arrays.toString(arr));
            }
        }
    }
    /**
     * 冒泡排序
     *
     * ①. 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * ②. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。
     * ③. 针对所有的元素重复以上的步骤，除了最后一个。
     * ④. 持续每次对越来越少的元素重复上面的步骤①~③，直到没有任何一对数字需要比较。
     * @param arr  待排序数组
     */
    public static void bubbleSort(int[] arr){
        for (int i=arr.length-1;i>0;i--){
            for(int j=0;j < i;j++){
                if (arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    System_out_println("Sorting: " + Arrays.toString(arr));
                }
            }
        }

    }
    /**
     * 快速排序（递归）
     *
     * ①. 从数列中挑出一个元素，称为"基准"（pivot）。
     * ②. 重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（相同的数可以到任一边）。在这个分区结束之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
     * ③. 递归地（recursively）把小于基准值元素的子数列和大于基准值元素的子数列排序。
     * @param arr   待排序数组
     * @param low   左边界
     * @param high  右边界
     */
    public static void quickSort(int[] arr, int low, int high){
        if(arr.length<0) return;
        if(low>high) return;
        int left = low;
        int right = high;

        int temp = arr[left];
        while(left<right){
            while(left<right&&arr[right]>=temp){
                right--;
            }
            arr[left] = arr[right];
            while(left<right&&arr[left]<=temp){
                left++;
            }
            arr[right]=arr[left];
        }
        arr[left] = temp;
        System.out.println("Sorting: " + Arrays.toString(arr));
        quickSort(arr, low, left-1);
        quickSort(arr, left+1, high);
    }
    /**
     * 快速排序（非递归）
     *
     * ①. 从数列中挑出一个元素，称为"基准"（pivot）。
     * ②. 重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（相同的数可以到任一边）。在这个分区结束之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
     * ③. 把分区之后两个区间的边界（low和high）压入栈保存，并循环①、②步骤
     * @param arr   待排序数组
     */
    public static void quickSortByStack(int[] arr){
        if(arr.length <= 0) return;
        Stack<Integer> stack = new Stack<Integer>();

        //初始状态的左右指针入栈
        stack.push(0);
        stack.push(arr.length - 1);
        while(!stack.isEmpty()){
            int high = stack.pop();     //出栈进行划分
            int low = stack.pop();

            int pivotIdx = partition(arr, low, high);

            //保存中间变量
            if(pivotIdx > low) {
                stack.push(low);
                stack.push(pivotIdx - 1);
            }
            if(pivotIdx < high && pivotIdx >= 0){
                stack.push(pivotIdx + 1);
                stack.push(high);
            }
        }
    }

    private static int partition(int[] arr, int low, int high){
        if(arr.length <= 0) return -1;
        if(low >= high) return -1;
        int l = low;
        int r = high;

        int pivot = arr[l];    //挖坑1：保存基准的值
        while(l < r){
            while(l < r && arr[r] >= pivot){  //坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
                r--;
            }
            arr[l] = arr[r];
            while(l < r && arr[l] <= pivot){   //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
                l++;
            }
            arr[r] = arr[l];
        }
        arr[l] = pivot;   //基准值填补到坑3中，准备分治递归快排
        return l;
    }

    /**
     * 归并排序（递归）
     *
     * ①. 将序列每相邻两个数字进行归并操作，形成 floor(n/2)个序列，排序后每个序列包含两个元素；
     * ②. 将上述序列再次归并，形成 floor(n/4)个序列，每个序列包含四个元素；
     * ③. 重复步骤②，直到所有元素排序完毕。
     * @param arr   待排序数组
     */
    public static int[] mergingSort(int[] arr){
        if(arr.length <= 1) return arr;
        int num = arr.length >> 1;
        int[] leftArr = Arrays.copyOfRange(arr,0,num);
        int[] rightArr = Arrays.copyOfRange(arr,num,arr.length);
        System.out.println("split two array: " + Arrays.toString(leftArr) + " And " + Arrays.toString(rightArr));
        return mergeTwoArray(mergingSort(leftArr),mergingSort(rightArr));
    }
    private static int[] mergeTwoArray(int[] arr1, int[] arr2){
        int i = 0, j = 0, k = 0;
        int[] result = new int[arr1.length+arr2.length];
        while(i < arr1.length && j < arr2.length){
            if(arr1[i] <= arr2[j]){
                result[k++] = arr1[i++];
            }else{
                result[k++] = arr2[j++];
            }
        }
        while(i < arr1.length){
            result[k++] = arr1[i++];
        }
        while(j < arr2.length){
            result[k++] = arr2[j++];
        }
        System.out.println("Merging: " + Arrays.toString(result));
        return result;
    }






}
