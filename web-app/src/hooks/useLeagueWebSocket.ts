'use client';

import { Client } from '@stomp/stompjs';
import { useEffect, useRef } from 'react';

export function useLeagueWebSocket(onLeagueUpdate: (league: any) => void) {
  const clientRef = useRef<Client | null>(null);

  useEffect(() => {
    const client = new Client({
      brokerURL: 'ws://localhost:8080/ws/league-events',
      reconnectDelay: 5000,
      debug: (str) => console.log('WebSocket:', str),
      onConnect: () => {
        console.log('✅ WebSocket connected');
        // Subscribe to league events
        client.subscribe('/topic/league-events', (message) => {
          const league = JSON.parse(message.body);
          console.log('🆕 New league via WebSocket:', league);
          onLeagueUpdate(league);
        });
      },
      onStompError: (frame) => {
        console.error('❌ WebSocket error:', frame);
      },
    });

    clientRef.current = client;
    client.activate();

    return () => {
      client.deactivate();
    };
  }, [onLeagueUpdate]);
}
