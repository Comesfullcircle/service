package org.delivery.storeadmin.domain.sse.connection.Ifs;

public interface ConnectionPoolIfs<T,R> {

    void addSession(T uniqueKey, R session);
    R getSession(T uniqueKey);

    void onCompletionCallback(R session);
}
