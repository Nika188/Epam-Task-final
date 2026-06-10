package edu.epam.fop.mocks;

import edu.epam.fop.mocks.client.ClientRepository;
import edu.epam.fop.mocks.client.ClientResponse;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;

public final class ClientService {

  private final ClientRepository client;

  public ClientService(ClientRepository client) {
    this.client = client;

    long id = client.definedId();
    ClientResponse response = new ClientResponse(id, "Lou", "Tenat");

    when(client.findById(id)).thenReturn(response);
    when(client.findById(not(eq(id)))).thenReturn(null);
  }
  
  public ClientResponse search(long id) {
    return client.findById(id);
  }
}
