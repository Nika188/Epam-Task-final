package edu.epam.fop.mocks;

import edu.epam.fop.mocks.client.ClientRepository;
import edu.epam.fop.mocks.client.ClientResponse;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public final class ClientService {

    private final ClientRepository client;

    public ClientService(ClientRepository client) {
        this.client = client;

        long id = client.definedId();
        when(client.findById(anyLong())).thenAnswer(invocation -> {
            long inputId = invocation.getArgument(0);

            if (inputId == id){
                if (id % 2 == 0){
                    return new ClientResponse(id, "Louisa", "Rodriguez");
                }else{
                    return new ClientResponse(id, "Lou", "Tenat");
                }
            }

            return null;
        });

    }

    public ClientResponse search(long id) {
        return client.findById(id);
    }
}
