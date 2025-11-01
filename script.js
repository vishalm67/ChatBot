const API_BASE_URL = 'http://localhost:8080/api/chat';
        
        // State
        let username = '';
        let sessionId = null;
        let isProcessing = false;

        // Elements
        const welcomeScreen = document.getElementById('welcomeScreen');
        const messagesArea = document.getElementById('messagesArea');
        const inputArea = document.getElementById('inputArea');
        const messageInput = document.getElementById('messageInput');
        const sendBtn = document.getElementById('sendBtn');
        const typingIndicator = document.getElementById('typingIndicator');
        const errorBanner = document.getElementById('errorBanner');

        // Auto-resize textarea
        messageInput.addEventListener('input', function() {
            this.style.height = 'auto';
            this.style.height = (this.scrollHeight) + 'px';
        });

        // Enter to send (Shift+Enter for new line)
        messageInput.addEventListener('keydown', function(e) {
            if (e.key === 'Enter' && !e.shiftKey) {
                e.preventDefault();
                sendMessage();
            }
        });

        // Start chat
        function startChat() {
            const input = document.getElementById('usernameInput');
            const name = input.value.trim();
            
            if (!name) {
                showError('Please enter your name');
                return;
            }

            username = name;
            welcomeScreen.classList.add('hidden');
            messagesArea.classList.remove('hidden');
            inputArea.classList.remove('hidden');
            
            addBotMessage(`Hello ${username}! How can I assist you today?`, 'GREETING');
            messageInput.focus();
        }

        // Quick start with pre-filled message
        function quickStart(message) {
            const input = document.getElementById('usernameInput');
            const name = input.value.trim();
            
            if (!name) {
                showError('Please enter your name first');
                return;
            }

            username = name;
            welcomeScreen.classList.add('hidden');
            messagesArea.classList.remove('hidden');
            inputArea.classList.remove('hidden');
            
            messageInput.value = message;
            messageInput.focus();
        }

        // Send message
        async function sendMessage() {
            const message = messageInput.value.trim();
            
            if (!message || isProcessing) return;

            isProcessing = true;
            sendBtn.disabled = true;

            addUserMessage(message);
            messageInput.value = '';
            messageInput.style.height = 'auto';
            
            showTyping();

            try {
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
                    throw new Error('Server error');
                }

                const data = await response.json();
                
                if (!sessionId) {
                    sessionId = data.sessionId;
                }

                hideTyping();
                addBotMessage(data.response, data.intent);

            } catch (error) {
                console.error('Error:', error);
                hideTyping();
                showError('Connection failed. Please check if the backend is running.');
                addBotMessage('I\'m having trouble connecting. Please try again later.', 'ERROR');
            } finally {
                isProcessing = false;
                sendBtn.disabled = false;
                messageInput.focus();
            }
        }

        // Add user message
        function addUserMessage(text) {
            const wrapper = document.createElement('div');
            wrapper.className = 'message-wrapper user';
            
            const time = new Date().toLocaleTimeString('en-US', { 
                hour: '2-digit', 
                minute: '2-digit' 
            });
            
            wrapper.innerHTML = `
                <div class="message">
                    <div class="message-bubble">${escapeHtml(text)}</div>
                    <div class="message-meta">
                        <span>${time}</span>
                    </div>
                </div>
            `;
            
            messagesArea.insertBefore(wrapper, typingIndicator);
            scrollToBottom();
        }

        // Add bot message
        function addBotMessage(text, intent = '') {
            const wrapper = document.createElement('div');
            wrapper.className = 'message-wrapper bot';
            
            const time = new Date().toLocaleTimeString('en-US', { 
                hour: '2-digit', 
                minute: '2-digit' 
            });
            
            let intentBadge = '';
            if (intent && intent !== 'ERROR' && intent !== 'UNKNOWN') {
                intentBadge = `<span class="intent-badge">${intent}</span>`;
            }
            
            wrapper.innerHTML = `
                <div class="message">
                    <div class="message-bubble">${escapeHtml(text)}</div>
                    <div class="message-meta">
                        <span>${time}</span>
                        ${intentBadge}
                    </div>
                </div>
            `;
            
            messagesArea.insertBefore(wrapper, typingIndicator);
            scrollToBottom();
        }

        // Show/hide typing
        function showTyping() {
            typingIndicator.classList.add('show');
            scrollToBottom();
        }

        function hideTyping() {
            typingIndicator.classList.remove('show');
        }

        // Scroll to bottom
        function scrollToBottom() {
            messagesArea.scrollTop = messagesArea.scrollHeight;
        }

        // Show error
        function showError(message) {
            document.getElementById('errorText').textContent = message;
            errorBanner.classList.add('show');
            
            setTimeout(() => {
                errorBanner.classList.remove('show');
            }, 5000);
        }

        // Clear chat
        function clearChat() {
            if (confirm('Clear all messages?')) {
                const messages = messagesArea.querySelectorAll('.message-wrapper');
                messages.forEach(msg => msg.remove());
            }
        }

        // Export chat
        function exportChat() {
            const messages = [];
            document.querySelectorAll('.message-wrapper').forEach(wrapper => {
                const isUser = wrapper.classList.contains('user');
                const text = wrapper.querySelector('.message-bubble').textContent;
                messages.push({
                    sender: isUser ? username : 'Bot',
                    message: text,
                    timestamp: new Date().toISOString()
                });
            });
            
            const dataStr = JSON.stringify(messages, null, 2);
            const dataBlob = new Blob([dataStr], { type: 'application/json' });
            const url = URL.createObjectURL(dataBlob);
            const link = document.createElement('a');
            link.href = url;
            link.download = `chat-export-${Date.now()}.json`;
            link.click();
            URL.revokeObjectURL(url);
        }

        // Escape HTML
        function escapeHtml(text) {
            const div = document.createElement('div');
            div.textContent = text;
            return div.innerHTML;
        }

        // Check backend on load
        window.addEventListener('load', async () => {
            try {
                const response = await fetch(`${API_BASE_URL}/health`);
                if (response.ok) {
                    console.log('✅ Backend connected');
                }
            } catch (error) {
                console.warn('⚠️ Backend not reachable');
            }
        });
