# Week 3 OOP Homework Assignment — Category A Problems

This branch contains clean, fully-tested Java implementations for the Week 3 Object-Oriented Programming problems:

1. **Library Fine System (`LibraryFineSystem.java`)**
   - Class: `BookIssue`
   - Demonstrates distinction between instance methods (`fineAmount()`, `isSeverelyOverdue()`) and static aggregation utilities (`totalFineCollected()`).

2. **Extending Employee Without Touching It (`EmployeeExtension.java`)**
   - Classes: `Employee`, `ManagerEmployee`, `InternEmployee`
   - Implements inheritance, method extension, salary capping, and polymorphic runtime dispatch with `instanceof`.

3. **Object References & Null Safety (`ParkingSlotAllocation.java`)**
   - Class: `ParkingSlot`
   - Demonstrates pass-by-reference mutation semantics in Java and guaranteed null-safe slot searching and allocation.

4. **Designing the Instance/Static Boundary (`LibraryMembershipSystem.java`)**
   - Class: `LibraryMember`
   - Explains the architectural bugs resulting from premature static field usage and contrasts with a correctly encapsulated instance design with auto-generated IDs.

5. **Capstone HR + Parking Allocation Mini-System (`CompanyHRMiniSystem.java`)**
   - Class: `CompanyEmployeeRecord`
   - Integrates composition, inheritance, static counters, and null safety into a unified enterprise domain model.

## Compilation & Execution
```bash
javac src/*.java
java -cp src LibraryFineSystem
java -cp src EmployeeExtension
java -cp src ParkingSlotAllocation
java -cp src LibraryMembershipSystem
java -cp src CompanyHRMiniSystem
```