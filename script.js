  const API_BASE_URL = 'http://localhost:8080/api/chat';
        
        // Global variables
        let username = '';
        let sessionId = null;
        let messageCount = 0;

        // Auto-resize textarea
        const messageInput = document.getElementById('messageInput');
        messageInput.addEventListener('input', function() {
            this.style.height = 'auto';
            this.style.height = (this.scrollHeight) + 'px';
        });

        // Enter key to send (Shift+Enter for new line)
        messageInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault();
                sendMessage();
            }
        });

        // Start chat function
        function startChat() {
            const usernameField = document.getElementById('usernameField');
            const name = usernameField.value.trim();
            
            if (!name) {
                showError('Please enter your name to start chatting');
                return;
            }

            username = name;
            
            // Hide username input, show message input
            document.getElementById('usernameInput').style.display = 'none';
            document.getElementById('messageInputArea').classList.add('active');
            
            // Hide welcome screen
            document.getElementById('welcomeScreen').style.display = 'none';
            
            // Show greeting message
            addBotMessage(`Hello ${username}! 👋 I'm your AI assistant. How can I help you today?`, 'GREETING');
            
            // Focus on message input
            messageInput.focus();
        }

        // Send message function
        async function sendMessage() {
            const message = messageInput.value.trim();
            
            if (!message) return;

            // Add user message to chat
            addUserMessage(message);
            
            // Clear input
            messageInput.value = '';
            messageInput.style.height = 'auto';
            
            // Show typing indicator
            showTypingIndicator();

            try {
                // Call API
                const response = await fetch(`${API_BASE_URL}/message`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        message: message,
                        username: username,
                        sessionId: sessionId
                    })
                });

                if (!response.ok) {
                    throw new Error('Failed to get response from server');
                }

                const data = await response.json();
                
                // Save session ID
                if (!sessionId) {
                    sessionId = data.sessionId;
                }

                // Hide typing indicator
                hideTypingIndicator();

                // Add bot response
                addBotMessage(data.response, data.intent, data.confidence);

            } catch (error) {
                console.error('Error:', error);
                hideTypingIndicator();
                showError('Failed to connect to chatbot. Make sure the backend is running on port 8080.');
                addBotMessage('Sorry, I\'m having trouble connecting right now. Please make sure the backend server is running.', 'ERROR');
            }
        }

        // Add user message to chat
        function addUserMessage(text) {
            const messagesContainer = document.getElementById('messagesContainer');
            const messageDiv = document.createElement('div');
            messageDiv.className = 'message user';
            
            const time = new Date().toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });
            
            messageDiv.innerHTML = `
                <div class="message-content">
                    <div>${text}</div>
                    <div class="message-time">${time}</div>
                </div>
                <div class="message-avatar">👤</div>
            `;
            
            messagesContainer.appendChild(messageDiv);
            scrollToBottom();
            messageCount++;
        }

        // Add bot message to chat
        function addBotMessage(text, intent = '', confidence = null) {
            const messagesContainer = document.getElementById('messagesContainer');
            const messageDiv = document.createElement('div');
            messageDiv.className = 'message bot';
            
            const time = new Date().toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });
            
            let intentBadge = '';
            if (intent && intent !== 'ERROR') {
                intentBadge = `<div class="intent-badge">${intent}</div>`;
            }
            
            messageDiv.innerHTML = `
                <div class="message-avatar">🤖</div>
                <div class="message-content">
                    <div>${text}</div>
                    ${intentBadge}
                    <div class="message-time">${time}</div>
                </div>
            `;
            
            messagesContainer.appendChild(messageDiv);
            scrollToBottom();
            messageCount++;
        }

        // Show/hide typing indicator
        function showTypingIndicator() {
            document.getElementById('typingIndicator').classList.add('show');
            scrollToBottom();
        }

        function hideTypingIndicator() {
            document.getElementById('typingIndicator').classList.remove('show');
        }

        // Scroll to bottom
        function scrollToBottom() {
            const messagesContainer = document.getElementById('messagesContainer');
            messagesContainer.scrollTop = messagesContainer.scrollHeight;
        }

        // Show error message
        function showError(message) {
            const errorDiv = document.getElementById('errorMessage');
            errorDiv.textContent = message;
            errorDiv.classList.add('show');
            
            setTimeout(() => {
                errorDiv.classList.remove('show');
            }, 5000);
        }

        // Suggestion chip click
        function suggestMessage(message) {
            if (username) {
                messageInput.value = message;
                messageInput.focus();
            }
        }

        // Clear chat
        function clearChat() {
            if (confirm('Are you sure you want to clear the chat?')) {
                const messagesContainer = document.getElementById('messagesContainer');
                messagesContainer.innerHTML = `
                    <div class="welcome-screen" id="welcomeScreen">
                        <div class="welcome-icon">👋</div>
                        <h3>Welcome Back!</h3>
                        <p>Chat cleared. Start a new conversation below.</p>
                    </div>
                    <div class="typing-indicator" id="typingIndicator">
                        <div class="message-avatar">🤖</div>
                        <div class="typing-dots">
                            <div class="typing-dot"></div>
                            <div class="typing-dot"></div>
                            <div class="typing-dot"></div>
                        </div>
                    </div>
                `;
                messageCount = 0;
            }
        }

        // Toggle settings (placeholder)
        function toggleSettings() {
            alert('Settings panel coming soon!\n\nFeatures:\n- Change theme\n- Export chat history\n- Clear session\n- API configuration');
        }

        // Check backend connection on load
        window.addEventListener('load', async () => {
            try {
                const response = await fetch(`${API_BASE_URL}/health`);
                if (response.ok) {
                    console.log('✅ Backend connected successfully');
                }
            } catch (error) {
                console.warn('⚠️ Backend not connected. Make sure Spring Boot app is running on port 8080');
            }
        });