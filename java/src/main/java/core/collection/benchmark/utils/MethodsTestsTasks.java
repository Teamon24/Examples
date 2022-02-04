package core.collection.benchmark.utils;

import core.collection.benchmark.pojo.MethodResult;

import java.util.List;
import java.util.concurrent.Callable;

public interface MethodsTestsTasks<E> extends Callable<List<MethodResult<E>>> {
}
