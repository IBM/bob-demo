# Function Calling Implementation

**Mode:** Quarkus Developer

**Context:**

Asks Bob to create a function calling for the search booking database interaction.

**Prompt:**

```
Expose the getBookingDetail method from BookingService as a Tool
```

**Result:**

* Bob annotates with `Tool` the repository code to search bookings from the LLM.
* Registers the Tool to the AI Service created previously.
* Modify the LLM prompt to indicate that it can use a tool to find book details.

---
