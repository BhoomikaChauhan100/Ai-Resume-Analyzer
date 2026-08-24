document.addEventListener("DOMContentLoaded", function () {

    const saveForms = document.querySelectorAll(".save-job-form");

    saveForms.forEach(function (form) {

        form.addEventListener("submit", async function (event) {

            event.preventDefault();

            const button =
                form.querySelector(".save-job-btn");

            button.disabled = true;
            button.innerHTML = "⏳ Saving...";

            try {

                const formData = new FormData(form);

                const response = await fetch(form.action, {
                    method: "POST",
                    body: formData,
                    credentials: "same-origin"
                });

                if (!response.ok) {
                    throw new Error("Failed to save job");
                }

                const data = await response.json();

                if (data.success) {

                    button.innerHTML = "✓ Job Saved";

                    button.classList.remove(
                        "btn-outline-success"
                    );

                    button.classList.add(
                        "btn-success"
                    );

                } else {

                    throw new Error(
                        data.message || "Could not save job"
                    );
                }

            } catch (error) {

                console.error("Save Job Error:", error);

                button.disabled = false;

                button.innerHTML = "💾 Save Job";

                alert(
                    "Unable to save the job. Please try again."
                );
            }

        });

    });

});