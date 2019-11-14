package com.alipay.sofa.rpc.filter;

import com.alipay.sofa.rpc.core.exception.SofaRpcRuntimeException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.ext.Extensible;

@Extensible(singleton = false)
public interface Filter {

    /**
     * Do filtering
     * <p>
     * <pre><code>
     *  doBeforeInvoke(); // the code before invoke, even new dummy response for return (skip all next invoke).
     *  SofaResponse response = invoker.invoke(request); // do next invoke(call next filter, call remote, call implements).
     *  doAfterInvoke(); // the code after invoke
     * </code></pre>
     *
     * @param invoker Invoker
     * @param request Request
     * @return SofaResponse Response
     * @throws SofaRpcRuntimeException Occur rpc exception
     */
    public abstract SofaResponse invoke(FilterInvoker invoker, SofaRequest request) throws SofaRpcRuntimeException;

    /**
     * Do filtering after asynchronous respond, only supported in CONSUMER SIDE. <p>
     *
     * Because when do async invoke, the code after invoke has been executed after invoker return dummy empty response.
     * We need execute filter code after get true response from server.<p>
     *
     * NOTICE: The thread run {@link #onAsyncResponse} is different with the thread run {@link #invoke}
     *
     * @param config    ConsumerConfig, READ ONLY PLEASE.
     * @param request   Request
     * @param response  Response from server (if exception is null)
     * @param exception Exception from server (if response is null)
     * @throws SofaRpcException Other rpc exception
     * @see #invoke(FilterInvoker, SofaRequest)
     * @see SofaRequest#isAsync()
     */
    //public void onAsyncResponse(ConsumerConfig config, SofaRequest request, SofaResponse response, Throwable exception) throws SofaRpcRuntimeException;

}
