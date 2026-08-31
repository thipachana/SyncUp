# ADR 004 – Verwendung einer REST-API zur Kommunikation

## Status

Akzeptiert

## Kontext

Das Frontend und das Backend von SyncUp müssen Daten austauschen. Dazu gehören unter anderem Benutzerinformationen, Kalender, Termine und Ressourcen.

## Alternativen

Als Alternativen wurden GraphQL sowie eine direkte Kopplung zwischen Frontend und Backend betrachtet.

GraphQL ermöglicht flexible Datenabfragen, erhöht jedoch die Komplexität der Anwendung. Eine direkte Kopplung würde die Wartbarkeit und Erweiterbarkeit der Software einschränken.

## Entscheidung

Die Kommunikation zwischen Frontend und Backend erfolgt über eine REST-API.

## Begründung

REST ist ein weit verbreiteter Standard für Webanwendungen und lässt sich problemlos mit React und Spring Boot kombinieren. Die einzelnen Funktionen können über klar definierte HTTP-Anfragen bereitgestellt werden. Dadurch bleiben Frontend und Backend voneinander getrennt und unabhängig.

## Konsequenzen

Alle Anfragen des Frontends werden über REST-Endpunkte an das Backend gesendet. Änderungen an den Schnittstellen müssen sowohl im Frontend als auch im Backend berücksichtigt werden.